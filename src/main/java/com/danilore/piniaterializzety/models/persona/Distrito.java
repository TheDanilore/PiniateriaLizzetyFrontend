/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.danilore.piniaterializzety.models.persona;

import java.util.Objects;

/**
 *
 * @author ASUS
 */
public class Distrito {

    private int idDistrito;
    private String descripcion;
    private Provincia provincia; // FK
    private String ubigeo;

    public Distrito() {
    }

    public Distrito(int idDistrito, String descripcion, Provincia provincia) {
        this.idDistrito = idDistrito;
        this.descripcion = descripcion;
        this.provincia = provincia;
    }

    public Distrito(int idDistrito, String descripcion, Provincia provincia, String ubigeo) {
        this.idDistrito = idDistrito;
        this.descripcion = descripcion;
        this.provincia = provincia;
        this.ubigeo = ubigeo;
    }

    public int getIdDistrito() {
        return idDistrito;
    }

    public void setIdDistrito(int idDistrito) {
        this.idDistrito = idDistrito;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public String getUbigeo() {
        return ubigeo;
    }

    public void setUbigeo(String ubigeo) {
        this.ubigeo = ubigeo;
    }

    @Override
    public String toString() {
        return descripcion;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Distrito that = (Distrito) obj;
        return Objects.equals(idDistrito, that.idDistrito); // Compara por ID
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDistrito); // Genera el hash basado en el ID
    }
}
