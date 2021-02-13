/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.modelo.dao;

import com.ipn.mx.modelo.dto.subCategoriaDTO;
import com.ipn.mx.modelo.entidades.subCategoria;
import com.ipn.mx.utilerias.HibernateUtil;
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
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author DANIEL
 */
public class subCategoriaDAO {
    
    public void create(subCategoriaDTO dto) {
        Session sesion = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = sesion.getTransaction();
        try {
            transaction.begin();
            sesion.save(dto.getEntidad());
            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public void update(subCategoriaDTO dto) throws SQLException {
        Session sesion = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = sesion.getTransaction();
        try {
            transaction.begin();
            sesion.update(dto.getEntidad());
            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public void delete(subCategoriaDTO dto) throws SQLException {
        Session sesion = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = sesion.getTransaction();
        try {
            transaction.begin();
            sesion.delete(dto.getEntidad());
            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public subCategoriaDTO read(subCategoriaDTO dto) throws SQLException {
        Session sesion = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = sesion.getTransaction();
        try {
            transaction.begin();
            dto.setEntidad(sesion.get(dto.getEntidad().getClass(), dto.getEntidad().getIdSubCategoria()));
            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
        return dto;
    }

    public List readALL(int idCat) {
        Session sesion = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = sesion.getTransaction();
        List lista = new ArrayList();
        try {
            transaction.begin();
            Query q = sesion.createQuery("from subCategoria p where idCategoria="+idCat+" order by p.idSubCategoria");
            for (subCategoria u : (List<subCategoria>) q.list()) {
                subCategoriaDTO dto = new subCategoriaDTO();
                dto.setEntidad(u);
                lista.add(dto);
            }
            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
        return lista;
    }
    
    public List readNames(int id){
        List dto = readALL(id);
        List names = new  ArrayList();
        for (int i = 0; i<dto.size();i++){
            subCategoriaDTO a = (subCategoriaDTO) dto.get(i);
            names.add(a.getEntidad().getNombreSubCategoria());
        }
        return names;
    }
    
    public List readIDs(int id){
        List dto = readALL(id);
        List names = new  ArrayList();
        for (int i = 0; i<dto.size();i++){
            subCategoriaDTO a = (subCategoriaDTO) dto.get(i);
            names.add(a.getEntidad().getIdSubCategoria());
        }
        return names;
    }
}

