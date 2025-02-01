/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.danilore.piniaterializzety.models.inventario;

import com.danilore.piniaterializzety.models.inventario.enums.TipoEntradaEnum;
import com.danilore.piniaterializzety.models.producto.Proveedor;
import com.danilore.piniaterializzety.models.usuario.Usuario;
import java.time.LocalDateTime;

/**
 *
 * @author artea
 */
public class EntradaProducto {

    private Long id;
    private Proveedor proveedor;
    private String guiaRemision;
    private TipoEntradaEnum tipoEntrada;
    private String procedencia;
    private LocalDateTime fecha;
    private Usuario usuario;
    private String observacion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public EntradaProducto() {
    }

    public EntradaProducto(Long id, Proveedor proveedor, String guiaRemision, TipoEntradaEnum tipoEntrada, String procedencia, LocalDateTime fecha, Usuario usuario, String observacion, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.proveedor = proveedor;
        this.guiaRemision = guiaRemision;
        this.tipoEntrada = tipoEntrada;
        this.procedencia = procedencia;
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

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public String getGuiaRemision() {
        return guiaRemision;
    }

    public void setGuiaRemision(String guiaRemision) {
        this.guiaRemision = guiaRemision;
    }

    public TipoEntradaEnum getTipoEntrada() {
        return tipoEntrada;
    }

    public void setTipoEntrada(TipoEntradaEnum tipoEntrada) {
        this.tipoEntrada = tipoEntrada;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
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
