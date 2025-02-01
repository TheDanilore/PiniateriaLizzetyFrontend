/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.danilore.piniaterializzety.models.inventario;

import com.danilore.piniaterializzety.models.inventario.enums.TipoSalidaEnum;
import com.danilore.piniaterializzety.models.usuario.Usuario;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author artea
 */
public class SalidaProducto {
    private Long id;
    private String guiaSalida;
    private TipoSalidaEnum tipoSalida;
    private String destino;
    private LocalDate fecha;
    private Usuario usuario;
    private String observacion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public SalidaProducto() {
    }

    public SalidaProducto(Long id, String guiaSalida, TipoSalidaEnum tipoSalida, String destino, LocalDate fecha, Usuario usuario, String observacion, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.guiaSalida = guiaSalida;
        this.tipoSalida = tipoSalida;
        this.destino = destino;
        this.fecha = fecha;
        this.usuario = usuario;
        this.observacion = observacion;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGuiaSalida() {
        return guiaSalida;
    }

    public void setGuiaSalida(String guiaSalida) {
        this.guiaSalida = guiaSalida;
    }

    public TipoSalidaEnum getTipoSalida() {
        return tipoSalida;
    }

    public void setTipoSalida(TipoSalidaEnum tipoSalida) {
        this.tipoSalida = tipoSalida;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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
    
    
}
