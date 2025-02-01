/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.danilore.piniaterializzety.models.inventario;

/**
 *
 * @author artea
 */
public class Variacion {

    private Long id;
    private Color color;
    private Longitud longitud;
    private Tamano tamano;

    public Variacion() {
    }

    public Variacion(Long id, Color color, Longitud longitud, Tamano tamano) {
        this.id = id;
        this.color = color;
        this.longitud = longitud;
        this.tamano = tamano;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Longitud getLongitud() {
        return longitud;
    }

    public void setLongitud(Longitud longitud) {
        this.longitud = longitud;
    }

    public Tamano getTamano() {
        return tamano;
    }

    public void setTamano(Tamano tamano) {
        this.tamano = tamano;
    }
    
    
}
