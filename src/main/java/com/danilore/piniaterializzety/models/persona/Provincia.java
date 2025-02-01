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
public class Provincia {

    private int idProvincia;
    private String descripcion;
    private Departamento departamento;  // FK

    public Provincia() {
    }

    public Provincia(int idProvincia, String descripcion, Departamento departamento) {
        this.idProvincia = idProvincia;
        this.descripcion = descripcion;
        this.departamento = departamento;
    }

    public int getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(int idProvincia) {
        this.idProvincia = idProvincia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
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
        Provincia that = (Provincia) obj;
        return Objects.equals(idProvincia, that.idProvincia); // Compara por ID
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProvincia); // Genera el hash basado en el ID
    }

}
