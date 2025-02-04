package com.danilore.piniaterializzety.models.usuario;
import java.time.LocalDateTime;

/**
 *
 * @author ASUS
 */
public class Permiso {

    private int id;
    private String descripcion;
    private String accion;// Identificador único de la acción (ej., "crear_usuario")
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Permiso() {
    }

    public Permiso(int id, String descripcion, String accion, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.descripcion = descripcion;
        this.accion = accion;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

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

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
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
        return "Permiso{"
                + "id=" + id
                + ", descripcion='" + descripcion + '\''
                + ", accion=" + accion
                + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt
                + '}';
    }

}
