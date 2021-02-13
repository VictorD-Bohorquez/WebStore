/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.controlador.web;

import com.ipn.mx.modelo.dao.GraficaDAO;
import com.ipn.mx.modelo.dao.UsuarioDAO;
import com.ipn.mx.modelo.dto.GraficaDTO;
import com.ipn.mx.modelo.dto.UsuarioDTO;
import com.ipn.mx.utilerias.Correos;
import com.ipn.mx.utilerias.HibernateUtil;
import com.ipn.mx.utilerias.LoginManagerVF;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.hibernate.Transaction;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.springframework.util.ResourceUtils;

/**
 *
 * @author DANIEL
 */
@WebServlet(name = "UsuarioServlet", urlPatterns = {"/UsuarioServlet"})
public class UsuarioServlet extends HttpServlet {

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
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion");
        switch(accion){
            case "listaDeUsuarios":
                listaDeUsuarios(request, response);
                break;
            case "nuevo":
                agregarUsuario(request, response);
                break;
            case "eliminar":
                eliminarUsuario(request, response);
                break;
            case "actualizar":
                actualizarUsuario(request, response);
                break;
            case "guardar":
                almacenarUsuario(request, response);
                break;
            case "ver":
                mostrarUsuario(request, response);
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    private void listaDeUsuarios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UsuarioDAO dao = new UsuarioDAO();
        List lista = dao.readAll();
        request.setAttribute("listaDeUsuarios", lista);
        RequestDispatcher vista = request.getRequestDispatcher("listaUsuarios.jsp");
        vista.forward(request, response);
    
    }

    private void agregarUsuario(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher rd = request.getRequestDispatcher("usuarioForm.jsp");
        try {
            rd.forward(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UsuarioDAO dao = new UsuarioDAO();
        UsuarioDTO dto = new UsuarioDTO();
        dto.getEntidad().setIdUsuario(Integer.parseInt(request.getParameter("id")));
        dao.delete(dto);
        listaDeUsuarios(request, response);
    }

    private void actualizarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UsuarioDAO dao = new UsuarioDAO();
        UsuarioDTO dto = new UsuarioDTO();
        dto.getEntidad().setIdUsuario(Integer.parseInt(request.getParameter("id")));
        dto = dao.read(dto);
        request.setAttribute("dto", dto);
        RequestDispatcher rd = request.getRequestDispatcher("usuarioForm.jsp");
        rd.forward(request, response);
    }

    private void almacenarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UsuarioDAO dao = new UsuarioDAO();
        UsuarioDTO dto = new UsuarioDTO();
        dto.getEntidad().setNombre(request.getParameter("txtnombre"));
        dto.getEntidad().setPaterno(request.getParameter("txtpaterno"));
        dto.getEntidad().setMaterno(request.getParameter("txtmaterno"));
        dto.getEntidad().setNombreUsuario(request.getParameter("txtusuario"));
        dto.getEntidad().setEmail(request.getParameter("txtemail"));
        dto.getEntidad().setTipoUsuario(request.getParameter("txttipo"));
        dto.getEntidad().setClaveUsuario(request.getParameter("txtclave"));
        log("Nombre: "+ dto.getEntidad().getNombre());
        LoginManagerVF x = new LoginManagerVF();
        String destino = request.getParameter("txtemail");
        if (x.isLogged(request, response)){
            HttpSession session = request.getSession();
            UsuarioDTO user = (UsuarioDTO) session.getAttribute("CurrentUSER");
            destino = user.getEntidad().getEmail();
        }
        Correos email = new Correos();
        if (request.getParameter("id") == null || request.getParameter("id").isEmpty()) { // Nuevo elemento
            dao.create(dto);
            email.nuevousuario(destino);
            if(request.getParameter("txttipo").equals("A"))
                listaDeUsuarios(request, response);
            else
                response.sendRedirect("index.jsp");
        } else { // actualizacion
            dto.getEntidad().setIdUsuario(Integer.parseInt(request.getParameter("id")));
            dao.update(dto);
            email.modusuario(destino, request.getParameter("txtusuario"));
            listaDeUsuarios(request, response);
        }
    }

    
    private void mostrarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UsuarioDAO dao = new UsuarioDAO();
        UsuarioDTO dto = new UsuarioDTO();
        dto.getEntidad().setIdUsuario(Integer.parseInt(request.getParameter("id")));
        RequestDispatcher rd = request.getRequestDispatcher("verUsuario.jsp");
        dto = dao.read(dto);
        request.setAttribute("usuario", dto);
        rd.forward(request, response); 
    }

    private void graficar(HttpServletRequest request, HttpServletResponse response) {
        JFreeChart grafica = ChartFactory.createPieChart("Usuarios Registrados", getGraficaProductos(), true, true, Locale.getDefault());
        String archivo = getServletConfig().getServletContext().getRealPath("/graficau.png");
        try {
            ChartUtils.saveChartAsPNG(new File(archivo), grafica, 500, 500);
            RequestDispatcher rd = request.getRequestDispatcher("graficaUsuario.jsp");
            rd.forward(request, response);
        } catch (IOException | ServletException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void verPDF(HttpServletRequest request, HttpServletResponse response) {
        GraficaDAO dao = new GraficaDAO();
        try {
            ServletOutputStream sos = response.getOutputStream();
            File reporte = new File(getServletConfig().getServletContext().getRealPath("/reportes/userr.jasper"));
            byte[] bytes = JasperRunManager.runReportToPdf(reporte.getPath(),null,dao.obtenerConexion());
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            sos.write(bytes, 0, bytes.length);
            sos.flush();
            sos.close();
        } catch (IOException | JRException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private PieDataset getGraficaProductos() {
        DefaultPieDataset pie3d = new DefaultPieDataset();
        GraficaDAO gdao = new GraficaDAO();
        try {
            List datos = gdao.grafica_usuarios();
            for (int i = 0; i < datos.size(); i++) {
                GraficaDTO dto = (GraficaDTO) datos.get(i);
                pie3d.setValue(dto.getNombre(), dto.getCantidad());
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pie3d;
    }
}
