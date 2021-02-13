/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.modelo.entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author DANIEL
 */
@Entity
@Table(name = "subCategoria",schema = "public")
public class subCategoria implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSubCategoria;
    private String nombreSubCategoria;
    private String descripcionSubCategoria;
    private int idCategoria;

    public subCategoria() {
    }

    public int getIdSubCategoria() {
        return idSubCategoria;
    }

    public String getNombreSubCategoria() {
        return nombreSubCategoria;
    }

    public String getDescripcionSubCategoria() {
        return descripcionSubCategoria;
    }
    
    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdSubCategoria(int idSubCategoria) {
        this.idSubCategoria = idSubCategoria;
    }

    public void setNombreSubCategoria(String nombreSubCategoria) {
        this.nombreSubCategoria = nombreSubCategoria;
    }

    public void setDescripcionSubCategoria(String descripcionSubCategoria) {
        this.descripcionSubCategoria = descripcionSubCategoria;
    }
    
    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
    
}