package com.danilore.piniaterializzety.models.usuario;

import com.danilore.piniaterializzety.models.EstadoEnum;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Danilore
 */
public class Usuario {

    private Long id;
    private String nombre;
    private String email;
    private String password;
    private String avatar;
    private EstadoEnum estado;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Rol> roles = new ArrayList<>();

    public Usuario() {
        this.roles = new ArrayList<>();
    }

    public Usuario(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Usuario(Long id, String nombre, String email, String password, String avatar, EstadoEnum estado, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.estado = estado;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public EstadoEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoEnum estado) {
        this.estado = estado;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public boolean tienePermiso(String permiso) {
        return this.getRoles().stream()
                .flatMap(rol -> rol.getPermisos().stream())
                .anyMatch(p -> p.getDescripcion().equalsIgnoreCase(permiso));
    }

    @Override
    public String toString() {
        return "Usuario{"
                + "id=" + id
                + ", nombre='" + nombre + '\''
                + ", email='" + email + '\''
                + ", estado=" + estado
                + ", roles=" + roles
                + '}';
    }
}
