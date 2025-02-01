/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.danilore.piniaterializzety.models.inventario;

import com.danilore.piniaterializzety.models.inventario.enums.TipoMovimientoEnum;
import com.danilore.piniaterializzety.models.usuario.Usuario;
import java.time.LocalDateTime;

/**
 *
 * @author artea
 */
public class MovimientoInventario {
    private Long id;
    private Inventario inventario;
    private TipoMovimientoEnum tipoMovimiento;
    private Long cantidad;
    private Long cantidadAnterior;
    private Long cantidadActual;
    private Usuario usuario;
    private String observacion;
    private LocalDateTime fecha;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public MovimientoInventario() {
    }

    public MovimientoInventario(Long id, Inventario inventario, TipoMovimientoEnum tipoMovimiento, Long cantidad, Long cantidadAnterior, Long cantidadActual, Usuario usuario, String observacion, LocalDateTime fecha, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.inventario = inventario;
        this.tipoMovimiento = tipoMovimiento;
        this.cantidad = cantidad;
        this.cantidadAnterior = cantidadAnterior;
        this.cantidadActual = cantidadActual;
        this.usuario = usuario;
        this.observacion = observacion;
        this.fecha = fecha;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public TipoMovimientoEnum getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(TipoMovimientoEnum tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public Long getCantidadAnterior() {
        return cantidadAnterior;
    }

    public void setCantidadAnterior(Long cantidadAnterior) {
        this.cantidadAnterior = cantidadAnterior;
    }

    public Long getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(Long cantidadActual) {
        this.cantidadActual = cantidadActual;
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

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
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
