/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.controlador.web;

import com.ipn.mx.modelo.dao.GraficaDAO;
import com.ipn.mx.modelo.dao.ProductoDAO;
import com.ipn.mx.modelo.dto.GraficaDTO;
import com.ipn.mx.modelo.dto.ProductoDTO;
import com.ipn.mx.modelo.dto.UsuarioDTO;
import com.ipn.mx.utilerias.Correos;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 *
 * @author DANIEL
 */
@WebServlet(name = "ProductoServlet", urlPatterns = {"/ProductoServlet"})
public class ProductoServlet extends HttpServlet {

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
            case "listaDeProductos":
                listaDeProductos(request, response);
                break;
            case "listaDeProductosUsuarios":
                listaDeProductosClientes(request, response);
                break;
            case "nuevo":
                agregarProducto(request, response);
                break;
            case "eliminar":
                eliminarProducto(request, response);
                break;
            case "actualizar":
                actualizarProducto(request, response);
                break;
            case "guardar":
                almacenarProducto(request, response);
                break;
            case "ver":
                mostrarProducto(request, response);
                break;
            case "graficar":
                graficar(request, response);
                break;
            case "verPDF":
                verPDF(request, response);
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

    private void agregarProducto(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher rd = request.getRequestDispatcher("productoForm.jsp");
        try {
            request.setAttribute("idcp", request.getParameter("idc"));
            rd.forward(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void eliminarProducto(HttpServletRequest request, HttpServletResponse response) {
        ProductoDAO dao = new ProductoDAO();
        ProductoDTO dto = new ProductoDTO();
        dto.getEntidad().setIdProducto(Integer.parseInt(request.getParameter("id")));
        ProductoDTO img = dao.read(dto);
        RequestDispatcher rd = request.getRequestDispatcher("ProductoServlet?accion=listaDeProductos&idc="+request.getParameter("idc"));
        dao.delete(dto);
        File imagen_anterior = new File(getServletConfig().getServletContext().getRealPath("/"+ img.getEntidad().getImagen()));
        imagen_anterior.delete();
        try {
            rd.forward(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(ProductoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void actualizarProducto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductoDAO dao = new ProductoDAO();
        ProductoDTO dto = new ProductoDTO();
        dto.getEntidad().setIdProducto(Integer.parseInt(request.getParameter("id")));
            dto = dao.read(dto);
            request.setAttribute("dto", dto);
            RequestDispatcher rd = request.getRequestDispatcher("productoForm.jsp");
            rd.forward(request, response);
    }

    private void almacenarProducto(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ProductoDAO dao = new ProductoDAO();
        ProductoDTO dto = new ProductoDTO();
        dto.getEntidad().setNombreProducto(request.getParameter("txtnombre"));
        dto.getEntidad().setDescripcionProducto(request.getParameter("txtdescripcion"));
        dto.getEntidad().setPrecio(Float.parseFloat(request.getParameter("txtprecio")));
        dto.getEntidad().setExistencia(Integer.parseInt(request.getParameter("txtexistencia")));
        Part archivo = request.getPart("txtimagen"); //llamada al parámetro foto de mi formulario.
        String context = request.getServletContext().getRealPath("fotos"); //img es la carpeta que he creado en mi proyecto, dentro de la carpeta Web Content.
        String foto = Paths.get(archivo.getSubmittedFileName()).getFileName().toString(); 
        archivo.write(context + File.separator + foto); // Escribimos el archivo al disco duro del servidor.
        String fotoname = context + File.separator + foto;
        dto.getEntidad().setImagen(fotoname);
        HttpSession session = request.getSession();
        UsuarioDTO user = (UsuarioDTO)session.getAttribute("CurrentUSER");
        String destino = user.getEntidad().getEmail();
        Correos email = new Correos();
        email.producto(destino, request.getParameter("txtnombre"));
        if (request.getParameter("id") == null || request.getParameter("id").isEmpty()) { // Nuevo elemento
            dto.getEntidad().setIdSubCategoria(Integer.parseInt(request.getParameter("idcp")));
            dao.create(dto);
            RequestDispatcher rd = request.getRequestDispatcher("ProductoServlet?accion=listaDeProductos&idc=" + request.getParameter("idcp"));
            try {
                rd.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(ProductoServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else { // actualizacion
            dto.getEntidad().setIdProducto(Integer.parseInt(request.getParameter("id")));
            dto.getEntidad().setIdSubCategoria(Integer.parseInt(request.getParameter("idc")));
            dao.update(dto);
            RequestDispatcher rd = request.getRequestDispatcher("ProductoServlet?accion=listaDeProductos&idc=" + request.getParameter("idc"));
            try {
                rd.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(ProductoServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void mostrarProducto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductoDAO dao = new ProductoDAO();
        ProductoDTO dto = new ProductoDTO();
        dto.getEntidad().setIdProducto(Integer.parseInt(request.getParameter("id")));
        RequestDispatcher rd = request.getRequestDispatcher("verProducto.jsp");
        dto = dao.read(dto);
        request.setAttribute("producto", dto);
        rd.forward(request, response);
    }

    private void graficar(HttpServletRequest request, HttpServletResponse response) {
        int idc = Integer.parseInt(request.getParameter("idc"));
        JFreeChart grafica = ChartFactory.createPieChart("Productos por Categoría", getGraficaProductos(idc), true, true, Locale.getDefault());
        String archivo = getServletConfig().getServletContext().getRealPath("/graficap.png");
        try {
            ChartUtils.saveChartAsPNG(new File(archivo), grafica, 500, 500);
            RequestDispatcher rd = request.getRequestDispatcher("graficaProducto.jsp");
            rd.forward(request, response);
        } catch (IOException | ServletException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private PieDataset getGraficaProductos(int idc) {
        DefaultPieDataset pie3d = new DefaultPieDataset();
        GraficaDAO gdao = new GraficaDAO();
        try {
            List datos = gdao.grafica_productos(idc);
            for (int i = 0; i < datos.size(); i++) {
                GraficaDTO dto = (GraficaDTO) datos.get(i);
                pie3d.setValue(dto.getNombre(), dto.getCantidad());
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pie3d;
    }

    private void verPDF(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void listaDeProductos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductoDAO dao = new ProductoDAO();
        List lista = dao.readAll(Integer.parseInt(request.getParameter("idc")));
        request.setAttribute("listaDeProductos", lista);
        request.setAttribute("idc", request.getParameter("idc"));
        RequestDispatcher vista = request.getRequestDispatcher("listaProductos.jsp");
        vista.forward(request, response);
    }
    
    private void listaDeProductosClientes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductoDAO dao = new ProductoDAO();
        List lista = dao.readAll(Integer.parseInt(request.getParameter("idc")));
        request.setAttribute("listaDeProductos", lista);
        request.setAttribute("idc", request.getParameter("idc"));
        RequestDispatcher vista = request.getRequestDispatcher("listaProductosClientes.jsp");
        vista.forward(request, response);
    }

}
