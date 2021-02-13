/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.controlador.web;

import com.ipn.mx.modelo.dao.UsuarioDAO;
import com.ipn.mx.modelo.dto.UsuarioDTO;
import com.ipn.mx.modelo.entidades.Usuario;
import com.ipn.mx.utilerias.LoginManagerVF;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author DANIEL
 */
@WebServlet(name = "login", urlPatterns = {"/login"})

public class login extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String usernick = request.getParameter("idu");
        String password = request.getParameter("password");
        UsuarioDTO user = new UsuarioDTO();
        UsuarioDAO u = new UsuarioDAO();
        Usuario us = u.findByUserNameAndPassword(usernick, password);
        user.setEntidad(us);
        if (us==null){
            log("El usuario es nulo");
            log(usernick);
            log(password);}
        if (user.getEntidad()!=null) {
            log("Aunque era nulo entre");
            HttpSession session = request.getSession();
            session.setAttribute("CurrentUSER", user);
            String tipo = us.getTipoUsuario();
            if (tipo.equals("A"))
                request.getRequestDispatcher("home.jsp").include(request, response);
            else{
                request.getRequestDispatcher("inicioCliente.jsp").include(request, response);
            }
        } else {
            log("Como es nulo lo saco");
            response.sendRedirect("index.jsp");
        }
        
    }
}
