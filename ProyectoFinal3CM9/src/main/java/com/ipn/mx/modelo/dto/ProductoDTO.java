/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.modelo.dto;

import com.ipn.mx.modelo.entidades.Producto;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author DANIEL
 */
@Data
@AllArgsConstructor
public class ProductoDTO {
   private Producto entidad;

    public ProductoDTO() {
        entidad = new Producto();
    }
   
   
}
