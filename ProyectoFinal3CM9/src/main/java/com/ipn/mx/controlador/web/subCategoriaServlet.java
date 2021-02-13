/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.controlador.web;

import com.ipn.mx.modelo.dao.GraficaDAO;
import com.ipn.mx.modelo.dao.subCategoriaDAO;
import com.ipn.mx.modelo.dto.GraficaDTO;
import com.ipn.mx.modelo.dto.subCategoriaDTO;
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
import net.sf.jasperreports.engine.JRException;
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
@WebServlet(name = "subCategoriaServlet", urlPatterns = {"/subCategoriaServlet"})
public class subCategoriaServlet extends HttpServlet {

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
            case "listaDeSubCategorias":
                listaDeSubCategorias(request, response);
                break;
            case "listaDeSubCategoriasUsuario":
                listaDeSubCategoriasUsuario(request, response);
                break;
            case "nuevo":
                agregarSubCategoria(request, response);
                break;
            case "eliminar":
                eliminarSubCategoria(request, response);
                break;
            case "actualizar":
                actualizarSubCategoria(request, response);
                break;
            case "guardar":
                almacenarSubCategoria(request, response);
                break;
            case "ver":
                mostrarSubCategoria(request, response);
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

    private void listaDeSubCategorias(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        subCategoriaDAO dao = new subCategoriaDAO();
        List lista = dao.readALL(Integer.parseInt(request.getParameter("idc")));
        request.setAttribute("listaDeSubCategorias", lista);
        request.setAttribute("idc", request.getParameter("idc"));
        RequestDispatcher vista = request.getRequestDispatcher("listaSubCategorias.jsp");
        vista.forward(request, response);
    }

    private void agregarSubCategoria(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher rd = request.getRequestDispatcher("subCategoriaForm.jsp");
        try {
            request.setAttribute("idcp", request.getParameter("idc"));
            rd.forward(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void eliminarSubCategoria(HttpServletRequest request, HttpServletResponse response) {
        subCategoriaDAO dao = new subCategoriaDAO();
        subCategoriaDTO dto = new subCategoriaDTO();
        dto.getEntidad().setIdSubCategoria(Integer.parseInt(request.getParameter("id")));
        RequestDispatcher rd = request.getRequestDispatcher("subCategoriaServlet?accion=listaDeSubCategorias&idc="+request.getParameter("idc"));
        try {
            dao.delete(dto);
            try {
                rd.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(ProductoServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void almacenarSubCategoria(HttpServletRequest request, HttpServletResponse response){
        subCategoriaDAO dao = new subCategoriaDAO();
        subCategoriaDTO dto = new subCategoriaDTO();
        dto.getEntidad().setNombreSubCategoria(request.getParameter("txtnombre"));
        dto.getEntidad().setDescripcionSubCategoria(request.getParameter("txtdescripcion"));
        HttpSession session = request.getSession();
        UsuarioDTO user = (UsuarioDTO)session.getAttribute("CurrentUSER");
        String destino = user.getEntidad().getEmail();
        Correos email = new Correos();
        email.subcategoria(destino, request.getParameter("txtnombre"));
        try {
            if (request.getParameter("id") == null || request.getParameter("id").isEmpty()) { // Nuevo elemento
                dto.getEntidad().setIdCategoria(Integer.parseInt(request.getParameter("idcp")));
                dao.create(dto);
                RequestDispatcher rd = request.getRequestDispatcher("subCategoriaServlet?accion=listaDeSubCategorias&idc="+request.getParameter("idcp"));
                try {
                rd.forward(request, response);
                } catch (ServletException | IOException ex) {
                    Logger.getLogger(ProductoServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else { // actualizacion
                dto.getEntidad().setIdSubCategoria(Integer.parseInt(request.getParameter("id")));
                dto.getEntidad().setIdCategoria(Integer.parseInt(request.getParameter("idc")));
                dao.update(dto);
                RequestDispatcher rd = request.getRequestDispatcher("subCategoriaServlet?accion=listaDeSubCategorias&idc="+request.getParameter("idc"));
                try {
                rd.forward(request, response);
                } catch (ServletException | IOException ex) {
                    Logger.getLogger(ProductoServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }    

    private void mostrarSubCategoria(HttpServletRequest request, HttpServletResponse response) {
        subCategoriaDAO dao = new subCategoriaDAO();
        subCategoriaDTO dto = new subCategoriaDTO();
        dto.getEntidad().setIdSubCategoria(Integer.parseInt(request.getParameter("id")));
        RequestDispatcher rd = request.getRequestDispatcher("versub.jsp");
        try {
            dto = dao.read(dto);
            request.setAttribute("subcategoria", dto);
            rd.forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void actualizarSubCategoria(HttpServletRequest request, HttpServletResponse response) {
            subCategoriaDAO dao = new subCategoriaDAO();
            subCategoriaDTO dto = new subCategoriaDTO();
            dto.getEntidad().setIdSubCategoria(Integer.parseInt(request.getParameter("id")));
        try {
            dto = dao.read(dto);
            request.setAttribute("dto", dto);
            RequestDispatcher rd = request.getRequestDispatcher("subCategoriaForm.jsp");
            rd.forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void graficar(HttpServletRequest request, HttpServletResponse response) {
        JFreeChart grafica = ChartFactory.createPieChart("Mapeo de SubCategoría", getGraficaProductos(), true, true, Locale.getDefault());
        String archivo = getServletConfig().getServletContext().getRealPath("/grafica.png");
        try {
            ChartUtils.saveChartAsPNG(new File(archivo), grafica, 500, 500);
            RequestDispatcher rd = request.getRequestDispatcher("graficaCategoria.jsp");
            rd.forward(request, response);
        } catch (IOException | ServletException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //i10N
    private PieDataset getGraficaProductos() {
        DefaultPieDataset pie3d = new DefaultPieDataset();
        GraficaDAO gdao = new GraficaDAO();
        try {
            List datos = gdao.grafica();
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
        subCategoriaDAO dao = new subCategoriaDAO();
        /*try {
            ServletOutputStream sos = response.getOutputStream();
            File reporte = new File(getServletConfig().getServletContext().getRealPath("/reportes/Cat.jasper"));
            byte[] bytes = JasperRunManager.runReportToPdf(reporte.getPath(),null,dao.obtenerConexion());
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            sos.write(bytes, 0, bytes.length);
            sos.flush();
            sos.close();
        } catch (IOException | JRException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
    private void listaDeSubCategoriasUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        subCategoriaDAO dao = new subCategoriaDAO();
        List lista = dao.readALL(Integer.parseInt(request.getParameter("idc")));
        request.setAttribute("listaDeSubCategorias", lista);
        request.setAttribute("idc", request.getParameter("idc"));
        RequestDispatcher vista = request.getRequestDispatcher("listaSubCategoriasClientes.jsp");
        vista.forward(request, response);
    }
}
