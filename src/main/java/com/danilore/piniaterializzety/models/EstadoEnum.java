/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.danilore.piniaterializzety.models;

/**
 *
 * @author ASUS
 */
public enum EstadoEnum {
    ACTIVO,
    INACTIVO;
    
    public static EstadoEnum fromString(String estado) {
        for (EstadoEnum e : values()) {
            if (e.name().equalsIgnoreCase(estado)) {
                return e;
            }
        }
        throw new IllegalArgumentException("Estado no válido: " + estado);
    }
}
