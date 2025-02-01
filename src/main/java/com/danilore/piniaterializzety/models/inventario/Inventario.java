/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.danilore.piniaterializzety.models.inventario;

import com.danilore.piniaterializzety.models.producto.Producto;
import java.time.LocalDateTime;

/**
 *
 * @author artea
 */
public class Inventario {

    private Long id;
    private Producto producto;
    private Variacion variacion;
    private float precioUnitario;
    private Long cantidad;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Inventario() {
    }

    public Inventario(Long id, Producto producto, Variacion variacion, float precioUnitario, Long cantidad, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.producto = producto;
        this.variacion = variacion;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Variacion getVariacion() {
        return variacion;
    }

    public void setVariacion(Variacion variacion) {
        this.variacion = variacion;
    }

    public float getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
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
