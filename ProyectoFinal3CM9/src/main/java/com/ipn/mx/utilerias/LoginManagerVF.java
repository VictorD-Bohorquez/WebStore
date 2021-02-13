/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.utilerias;

import com.ipn.mx.modelo.dto.UsuarioDTO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 *
 * @author DANIEL
 */
public class LoginManagerVF {
   
   public void logout (HttpServletRequest request, HttpServletResponse response){
       HttpSession session = request.getSession(false);
       if(session != null){
           session.invalidate();
       } 
   }
   
   public boolean isLogged (HttpServletRequest request, HttpServletResponse response){
       HttpSession session = request.getSession(false);
       if(session == null){
           return false;
       } else{
           return session.getAttribute("CurrentUSER")!=null;
       }
   }
   
   public boolean isAdm (HttpServletRequest request, HttpServletResponse response){
       boolean a= false;
       if(isLogged(request,response)){
           HttpSession session = request.getSession(false);
           UsuarioDTO x = (UsuarioDTO) session.getAttribute("CurrentUSER");
           if(x.getEntidad().getTipoUsuario().equals("A"))
               a=true;
       } 
       return a;
   }
   
   
}
