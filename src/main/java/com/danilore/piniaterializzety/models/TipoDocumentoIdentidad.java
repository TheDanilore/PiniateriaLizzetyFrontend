/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.danilore.piniaterializzety.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author DANILORE
 */
public class TipoDocumentoIdentidad {

    private String id;
    private String abreviatura;
    private String descripcion;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;

    public TipoDocumentoIdentidad() {
    }

    public TipoDocumentoIdentidad(String id, String abreviatura, String descripcion) {
        this.id = id;
        this.abreviatura = abreviatura;
        this.descripcion = descripcion;
    }

    public TipoDocumentoIdentidad(String id, String abreviatura, String descripcion, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.abreviatura = abreviatura;
        this.descripcion = descripcion;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return this.abreviatura != null ? this.abreviatura : "";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TipoDocumentoIdentidad that = (TipoDocumentoIdentidad) obj;
        return Objects.equals(id, that.id); // Compara por ID
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // Genera el hash basado en el ID
    }

}
