package com.danilore.piniaterializzety.models.usuario;

import com.danilore.piniaterializzety.models.EstadoEnum;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Danilore
 */
public class Rol {

    private int id;
    private String descripcion;
    private EstadoEnum estado;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Permiso> permisos = new ArrayList<>();

    // Constructor vacío
    public Rol() {
        this.permisos = new ArrayList<>(); // Inicializamos la lista de permisos para evitar NullPointerException
    }

    public Rol(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Rol(int id, String descripcion, EstadoEnum estado, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.descripcion = descripcion;
        this.estado = estado;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoEnum estado) {
        this.estado = estado;
    }

    public List<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<Permiso> permisos) {
        this.permisos = permisos != null ? permisos : new ArrayList<>(); // Validamos que no sea null
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


    // Sobrescribir toString (opcional, para debugging o logs)
    @Override
    public String toString() {
        return "Rol{"
                + "id=" + id
                + ", descripcion='" + descripcion + '\''
                + ", estado=" + estado
                + ", permisos=" + permisos
                + '}';
    }

}
