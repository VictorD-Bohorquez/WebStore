/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.controlador.web;

import com.ipn.mx.utilerias.LoginManagerVF;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;  
import java.io.PrintWriter;  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;
/**
 *
 * @author DANIEL
 */
@WebServlet(name = "logout", urlPatterns = {"/logout"})  
public class logout extends HttpServlet {  
    
        protected void doGet(HttpServletRequest request, HttpServletResponse response)  
                                throws ServletException, IOException {  
            response.setContentType("text/html");  
            PrintWriter out=response.getWriter();  
            String despedida = "Adios";
            request.getRequestDispatcher("index.jsp").include(request, response);  
            LoginManagerVF manejador = new LoginManagerVF();
            manejador.logout(request, response);
            HttpSession session=request.getSession();  
            session.invalidate();  
            out.close();  
    }  
}  
