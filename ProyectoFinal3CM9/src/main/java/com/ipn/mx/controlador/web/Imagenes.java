/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.controlador.web;

import com.ipn.mx.modelo.dao.ProductoDAO;
import com.ipn.mx.modelo.dto.ProductoDTO;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author DANIEL
 */
@WebServlet(name = "Imagenes", urlPatterns = {"/Imagenes"})
public class Imagenes extends HttpServlet {

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
         switch(accion){
            case "guardar":
                ProductoDTO dto = new ProductoDTO();
                String ruta="";
                ArrayList<String> lista = new ArrayList<>();
                int a= 1;
                try{
                    FileItemFactory file = new DiskFileItemFactory();
                    ServletFileUpload fileUpload = new ServletFileUpload(file);
                    List items = fileUpload.parseRequest(request);
                    for (int i = 0; i < items.size(); i++) {
                        FileItem fileItem = (FileItem) items.get(i);
                        if (!fileItem.isFormField()) {
                            if (fileItem.getSize() > 0) {
                                File f = new File(getServletConfig().getServletContext().getRealPath("/fotos/") + fileItem.getName());
                                fileItem.write(f);
                                ruta = "fotos/" + fileItem.getName();
                        }
                            
                        } else {
                                lista.add(fileItem.getString());
                            }
                    }
                        
                    if (lista.size() == 8) {
                        if(lista.get(1).isEmpty() && lista.get(2).isEmpty()){
                            dto.getEntidad().setIdSubCategoria(Integer.parseInt(lista.get(0)));
                            dto.getEntidad().setNombreProducto(lista.get(3));
                            dto.getEntidad().setDescripcionProducto(lista.get(4));
                            dto.getEntidad().setPrecio(Float.parseFloat(lista.get(5)));
                            dto.getEntidad().setExistencia(Integer.parseInt(lista.get(6)));
                            dto.getEntidad().setImagen(ruta);
                            ProductoDAO dao = new ProductoDAO();
                            a = Integer.parseInt(lista.get(0));
                            log(dto.getEntidad().toString());
                            dao.create(dto);
                        
                        }
                        else{
                            dto.getEntidad().setIdProducto(Integer.parseInt(lista.get(1)));
                            dto.getEntidad().setIdSubCategoria(Integer.parseInt(lista.get(2)));
                            dto.getEntidad().setNombreProducto(lista.get(3));
                            dto.getEntidad().setDescripcionProducto(lista.get(4));
                            dto.getEntidad().setPrecio(Float.parseFloat(lista.get(5)));
                            dto.getEntidad().setExistencia(Integer.parseInt(lista.get(6)));
                            if (ruta.isEmpty())
                                dto.getEntidad().setImagen(lista.get(7));
                            else{
                                dto.getEntidad().setImagen(ruta);
                                File imagen_anterior = new File(getServletConfig().getServletContext().getRealPath("/"+lista.get(7)));
                                imagen_anterior.delete();
                            }
                            ProductoDAO dao = new ProductoDAO();
                            a = Integer.parseInt(lista.get(2));
                            dao.update(dto);
                        }
                    }
                    if (lista.size()== 6){
                        log("Entre directo");
                        dto.getEntidad().setIdSubCategoria(Integer.parseInt(lista.get(1)));
                        dto.getEntidad().setNombreProducto(lista.get(2));
                        dto.getEntidad().setDescripcionProducto(lista.get(3));
                        dto.getEntidad().setPrecio(Float.parseFloat(lista.get(4)));
                        dto.getEntidad().setExistencia(Integer.parseInt(lista.get(5)));
                        dto.getEntidad().setImagen(ruta);
                        ProductoDAO dao = new ProductoDAO();
                        a = Integer.parseInt(lista.get(0));
                        dao.create(dto);
                    }
                }catch (Exception e){
                    
                }
                request.setAttribute("idc",a);
                request.getRequestDispatcher("ProductoServlet?accion=listaDeProductos&idc="+a).forward(request, response);
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

}
