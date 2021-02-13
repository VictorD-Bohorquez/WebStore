/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.controlador.web;

import com.ipn.mx.modelo.dao.ProductoDAO;
import com.ipn.mx.modelo.dto.ProductoDTO;
import com.ipn.mx.modelo.dto.UsuarioDTO;
import com.ipn.mx.utilerias.Correos;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author DANIEL
 */
@WebServlet(name = "CarritoServlet", urlPatterns = {"/CarritoServlet"})
public class CarritoServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        switch (accion) {
            case "agregarProducto":
                agregarProducto(request, response);
                break;
            case "verProductos":
                verProductos(request, response);
                break;
            case "actualizar":
                actualizar(request, response);
                break;
            case "comprar":
                comprarCarrito(request, response);
                break;
            default:
                break;
        }
    }    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

     private void agregarProducto(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int producto = Integer.parseInt(request.getParameter("id"));
        int idcat = Integer.parseInt(request.getParameter("idc"));
        List productos = new ArrayList();
        List cantidad = new ArrayList<Integer>();

        if (request.getSession().getAttribute("productos") == null) {
            productos.add(producto);
            request.getSession().setAttribute("productos", productos);
            cantidad.add(1);
            request.getSession().setAttribute("cantidad", cantidad);

        } else {
            productos = (ArrayList<Integer>) request.getSession().getAttribute("productos");
            cantidad = (ArrayList<Integer>) request.getSession().getAttribute("cantidad");

            if (productos.contains(producto)) {
                int id = productos.indexOf(producto);
                int pos = (int) cantidad.get(id);
                cantidad.set(id, pos + 1);
                request.getSession().setAttribute("cantidad", cantidad);

            } else {
                productos.add(producto);
                request.getSession().setAttribute("productos", productos);
                cantidad.add(1);
                request.getSession().setAttribute("cantidad", cantidad);
            }
        }
        //RequestDispatcher vista = request.getRequestDispatcher("carrito.jsp");
        //vista.forward(request, response);
        response.sendRedirect("ProductoServlet?accion=listaDeProductosUsuarios&idc="+idcat);
    }

    private void verProductos(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ProductoDAO dao = new ProductoDAO();
        ProductoDTO dto = new ProductoDTO();
        int total = 0;

        ArrayList<Integer> productos = (ArrayList<Integer>) request.getSession().getAttribute("productos");
        ArrayList<Integer> cantidad = (ArrayList<Integer>) request.getSession().getAttribute("cantidad");
        List lista = new ArrayList();

        if (productos == null) {
            response.sendRedirect("carrito.jsp");
        } else {
                for (int i = 0; i < productos.size(); i++) {
                dto.getEntidad().setIdProducto(productos.get(i));
                dto = dao.read(dto);
                lista.add(dto);
                for (int j = 0; j < cantidad.get(i); j++) {
                    total = (int) (total + dto.getEntidad().getPrecio());
                }
            }
            request.getSession().setAttribute("carritoProductos", lista);
            request.getSession().setAttribute("cantidad", cantidad);
            request.getSession().setAttribute("total", total);
            RequestDispatcher vista = request.getRequestDispatcher("carrito.jsp");
            vista.forward(request, response);

            
        }

    }

    private void actualizar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int cant = Integer.parseInt(request.getParameter("cant"));
        ArrayList<Integer> cantidad = (ArrayList<Integer>) request.getSession().getAttribute("cantidad");

        int actual = cantidad.get(id);
        cantidad.set(id, actual + cant);

        if (cantidad.get(id) == 0) {
            ArrayList<Integer> productos = (ArrayList<Integer>) request.getSession().getAttribute("productos");
            cantidad.remove(id);
            productos.remove(id);
            request.getSession().setAttribute("productos", productos);
        }

        request.getSession().setAttribute("cantidad", cantidad);
        response.sendRedirect("CarritoServlet?accion=verProductos");
        //cantidad.set(id, pos + 1);
    }

    private void comprarCarrito(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ProductoDAO dao = new ProductoDAO();
        ProductoDTO dto = new ProductoDTO();
        ArrayList<Integer> productos = (ArrayList<Integer>) request.getSession().getAttribute("productos");
        ArrayList<Integer> cantidad = (ArrayList<Integer>) request.getSession().getAttribute("cantidad");
        int cant = Integer.parseInt(request.getParameter("cant"));

        //Actualización de la existencia
        for (int i = 0; i < productos.size(); i++) {
            dto.getEntidad().setIdProducto(productos.get(i));
            dto = dao.read(dto);
            int exisAct = dto.getEntidad().getExistencia();
            dto.getEntidad().setExistencia(exisAct - cantidad.get(i));
            dao.update(dto);
        }
        response.sendRedirect("inicioCliente.jsp");

        //Envio de correo
//            UsuarioDTO user = (UsuarioDTO) request.getSession().getAttribute("CurrentUSER");
//            Correos mail = new Correos();
//            String email = user.getEntidad().getEmail();
//            String cuerpo = user.getEntidad().getNombre() + "\nHola, somos el equipo de tiendaVirtual y estamos muy agradecidos por tu compra la cual fue por una cantidad de: " + cant;
//            mail.compra(email, cuerpo);
        //Borrado del carrito
        productos = new ArrayList();
        request.getSession().setAttribute("productos", productos);
        request.getSession().setAttribute("cantidad", productos);
    }
}
