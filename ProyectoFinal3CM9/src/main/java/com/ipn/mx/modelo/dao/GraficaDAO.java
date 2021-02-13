/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.modelo.dao;

import com.ipn.mx.modelo.dto.GraficaDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author DANIEL
 */
public class GraficaDAO {

    private static final String SQL_GRAFICAR = "select c.nombreSubCategoria, count(*) as productos"
            + " from Producto p, subCategoria c where p.idSubCategoria = c.idSubCategoria group by c.nombreSubCategoria";
    private static final String SQL_GRAFICAR_PRODUCTOS = "select nombreProducto, existencia from Producto where idSubCategoria = ?";
    private static final String SQL_GRAFICAR_USUARIOS = "select nombreUsuario from Usuario";
    private Connection con;

    public Connection obtenerConexion() {
        String user = "user";
        String pwd = "password";
        String url = "jdbc:postgresql://server:5432/database";
        String driverSQL = "org.postgresql.Driver";
        try {
            Class.forName(driverSQL);
            con = DriverManager.getConnection(url, user, pwd);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CategoriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
   
    public List grafica() throws SQLException {
        obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List lista = new ArrayList();
        try {
            ps = con.prepareStatement(SQL_GRAFICAR);
            rs = ps.executeQuery();
            while (rs.next()) {
                GraficaDTO gdto = new GraficaDTO();
                gdto.setNombre(rs.getString("nombreSubCategoria"));
                gdto.setCantidad(rs.getInt("productos"));
                lista.add(gdto);

            }
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
        return lista;
    }
    
    public List grafica_productos(int idc) throws SQLException{
        obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List lista = new ArrayList();
        try {
            ps = con.prepareStatement(SQL_GRAFICAR_PRODUCTOS);
            ps.setInt(1, idc);
            rs = ps.executeQuery();
            while (rs.next()) {
                GraficaDTO gdto = new GraficaDTO();
                gdto.setNombre(rs.getString("nombreProducto"));
                gdto.setCantidad(rs.getInt("existencia"));
                lista.add(gdto);

            }
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
        return lista; 
    }
    
    public List grafica_usuarios() throws SQLException {
        obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List lista = new ArrayList();
        try {
            ps = con.prepareStatement(SQL_GRAFICAR_USUARIOS);
            rs = ps.executeQuery();
            while (rs.next()) {
                GraficaDTO gdto = new GraficaDTO();
                gdto.setNombre(rs.getString("nombreUsuario"));
                gdto.setCantidad(1);
                lista.add(gdto);

            }
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
        return lista;
    }
}
