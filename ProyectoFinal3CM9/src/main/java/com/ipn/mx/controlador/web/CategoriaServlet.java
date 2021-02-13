/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.controlador.web;

import com.ipn.mx.modelo.dao.CategoriaDAO;
import com.ipn.mx.modelo.dao.GraficaDAO;
import com.ipn.mx.modelo.dto.CategoriaDTO;
import com.ipn.mx.modelo.dto.GraficaDTO;
import com.ipn.mx.modelo.dto.UsuarioDTO;
import com.ipn.mx.utilerias.Correos;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;
import jodd.util.CharUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 *
 * @author DANIEL
 */
@WebServlet(name = "CategoriaServlet", urlPatterns = {"/CategoriaServlet"})
public class CategoriaServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        
        String accion = request.getParameter("accion");
        switch(accion){
            case "listaDeCategoriasUsuario":
                listaDeCategoriasClientes(request, response);
                break;
            case "listaDeCategorias":
                listaDeCategorias(request, response);
                break;
            case "nuevo":
                agregarCategoria(request, response);
                break;
            case "eliminar":
                eliminarCategoria(request, response);
                break;
            case "actualizar":
                actualizarCategoria(request, response);
                break;
            case "guardar":
                almacenarCategoria(request, response);
                break;
            case "ver":
                mostrarCategoria(request, response);
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

    private void listaDeCategorias(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategoriaDAO dao = new CategoriaDAO();
        List lista = dao.readAll();
        request.setAttribute("listaDeCategorias", lista);
        RequestDispatcher vista = request.getRequestDispatcher("listaCategorias.jsp");
        vista.forward(request, response);
    }

    private void agregarCategoria(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher rd = request.getRequestDispatcher("categoriaForm.jsp");
        try {
            rd.forward(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void eliminarCategoria(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategoriaDAO dao = new CategoriaDAO();
            CategoriaDTO dto = new CategoriaDTO();
            dto.getEntidad().setIdCategoria(Integer.parseInt(request.getParameter("id")));
            dao.delete(dto);
            listaDeCategorias(request, response);
    }

    private void almacenarCategoria(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        CategoriaDAO dao = new CategoriaDAO();
        CategoriaDTO dto = new CategoriaDTO();
        dto.getEntidad().setNombreCategoria(request.getParameter("txtnombre"));
        dto.getEntidad().setDescripcionCategoria(request.getParameter("txtdescripcion"));
        log(request.getParameter("txtnombre"));
        log(request.getParameter("txtdescripcion"));
        HttpSession session = request.getSession();
        UsuarioDTO user = (UsuarioDTO)session.getAttribute("CurrentUSER");
        String destino = user.getEntidad().getEmail();
        Correos email = new Correos();
        email.categoria(destino, request.getParameter("txtnombre"));
        if (request.getParameter("id") == null || request.getParameter("id").isEmpty()) { // Nuevo elemento
            dao.create(dto);
            listaDeCategorias(request, response);
        } else { // actualizacion
            dto.getEntidad().setIdCategoria(Integer.parseInt(request.getParameter("id")));
            dao.update(dto);
            listaDeCategorias(request, response);
        }

    }    

    private void mostrarCategoria(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategoriaDAO dao = new CategoriaDAO();
        CategoriaDTO dto = new CategoriaDTO();
        dto.getEntidad().setIdCategoria(Integer.parseInt(request.getParameter("id")));
        RequestDispatcher rd = request.getRequestDispatcher("ver.jsp");
        dto = dao.read(dto);
        request.setAttribute("categoria", dto);
        rd.forward(request, response);
    }

    private void actualizarCategoria(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            CategoriaDAO dao = new CategoriaDAO();
            CategoriaDTO dto = new CategoriaDTO();
            dto.getEntidad().setIdCategoria(Integer.parseInt(request.getParameter("id")));
            dto = dao.read(dto);
            request.setAttribute("dto", dto);
            RequestDispatcher rd = request.getRequestDispatcher("categoriaForm.jsp");
            rd.forward(request, response);
    }
    
    private void listaDeCategoriasClientes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategoriaDAO dao = new CategoriaDAO();
        List lista = dao.readAll();
        request.setAttribute("listaDeCategorias", lista);
        RequestDispatcher vista = request.getRequestDispatcher("listaCategoriasClientes.jsp");
        vista.forward(request, response);
    }

}
