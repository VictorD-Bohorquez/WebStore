/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.utilerias;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author DANIEL
 */
public class Correos {
    public void enviarCorreo(String destinatario, String asunto, String mensaje){
        try {
            //Propiedades de la conexion
            Properties prop = new Properties();
            prop.setProperty("mail.smtp.host", "smtp.gmail.com");
            prop.setProperty("mail.smtp.starttls.enable", "true");
            prop.setProperty("mail.smtp.port", "587");
            prop.setProperty("mail.smtp.user", "practicaswad3cm9@gmail.com");
            prop.setProperty("mail.smtp.auth", "true");
            
            //Sesion
            Session session = Session.getDefaultInstance(prop);
            //Trabajar con el mensaje
            MimeMessage mensaje_clase = new MimeMessage(session);
            mensaje_clase.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            mensaje_clase.addRecipient(Message.RecipientType.TO, new InternetAddress("practicaswad3cm9@gmail.com"));
            mensaje_clase.setSubject(asunto);
            mensaje_clase.setText(mensaje);
            Transport t = session.getTransport("smtp");
            t.connect(prop.getProperty("mail.smtp.user"),"practicas192021");
            t.sendMessage(mensaje_clase, mensaje_clase.getAllRecipients());
            t.close();
        } catch (AddressException ex) {
            Logger.getLogger(Correos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(Correos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void nuevousuario(String destino) {
        Correos email = new Correos();
        String asusnto = "Bienvenida";
        String texto = "Hola, bienvenido a su nueva plataforma de ventas en linea. Saludos!";
        email.enviarCorreo(destino, asusnto, texto);
    }
    
    public void modusuario(String destino, String nick) {
        Correos email = new Correos();
        String asusnto = "Modificación de usuario";
        String texto = "Se ha modificado el usuario: "+nick+" de la base de datos";
        email.enviarCorreo(destino, asusnto, texto);
    }
    
    public void producto(String destino, String p) {
        Correos email = new Correos();
        String asusnto = "Modificación Productos";
        String texto = "Se ha modificado el produto: "+p+" en la base de datos";
        email.enviarCorreo(destino, asusnto, texto);
    }
    
    public void categoria(String destino, String p) {
        Correos email = new Correos();
        String asusnto = "Modificación de Categoria";
        String texto = "Se ha modificado la categoria: "+p+" en la base de datos";
        email.enviarCorreo(destino, asusnto, texto);
    }
    
    public void subcategoria(String destino, String p) {
        Correos email = new Correos();
        String asusnto = "Modificación de SubCategoria";
        String texto = "Se ha modificado la subcategoria: "+p+" en la base de datos";
        email.enviarCorreo(destino, asusnto, texto);
    }
    public void compra(String destino, String p) {
        Correos email = new Correos();
        String asusnto = "Compra en tiendaVirtual";
        email.enviarCorreo(destino, asusnto, p);
    }
}
