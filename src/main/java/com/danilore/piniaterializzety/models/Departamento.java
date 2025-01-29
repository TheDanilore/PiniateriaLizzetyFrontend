/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.danilore.piniaterializzety.models;

import java.util.Objects;

/**
 *
 * @author ASUS
 */
public class Departamento {

    private int idDepartamento;
    private String descripcion;

    public Departamento() {
    }

    public Departamento(int idDepartamento, String descripcion) {
        this.idDepartamento = idDepartamento;
        this.descripcion = descripcion;
    }

    public int getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // Opcional: para que el comboBox muestre la descripción
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
        Departamento that = (Departamento) obj;
        return Objects.equals(idDepartamento, that.idDepartamento); // Compara por ID
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDepartamento); // Genera el hash basado en el ID
    }
}
