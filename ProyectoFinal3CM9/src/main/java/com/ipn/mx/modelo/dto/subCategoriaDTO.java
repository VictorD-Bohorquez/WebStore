/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.modelo.dto;

import com.ipn.mx.modelo.entidades.subCategoria;
import java.io.Serializable;

/**
 *
 * @author DANIEL
 */
public class subCategoriaDTO implements Serializable{
    private subCategoria entidad;

    public subCategoriaDTO() {
        entidad = new subCategoria();
    }

    public subCategoriaDTO(subCategoria entidad) {
        this.entidad = entidad;
    }

    public subCategoria getEntidad() {
        return entidad;
    }

    public void setEntidad(subCategoria entidad) {
        this.entidad = entidad;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID subCategoría: ").append(getEntidad().getIdSubCategoria()).append("\n");
        sb.append("Nombre subCategoría: ").append(getEntidad().getNombreSubCategoria()).append("\n");
        sb.append("Descripción subCategoría: ").append(getEntidad().getDescripcionSubCategoria()).append("\n");
        sb.append("ID Categoria: ").append(getEntidad().getIdCategoria()).append("\n");
        return sb.toString();
    }    
    
}
