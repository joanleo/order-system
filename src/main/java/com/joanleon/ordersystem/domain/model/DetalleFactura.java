package com.joanleon.ordersystem.domain.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DetalleFactura {
    private Long id;
    private Producto producto;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal descuentoLinea;
    private BigDecimal subtotal;

    public DetalleFactura(Producto producto, Integer cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecio();
        this.descuentoLinea = BigDecimal.ZERO;
        calcularSubtotal();
    }
    
    public DetalleFactura(Producto producto, Integer cantidad, BigDecimal descuentoLinea) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecio();
        this.descuentoLinea = descuentoLinea != null ? descuentoLinea : BigDecimal.ZERO;
        calcularSubtotal();
    }
    
    private void calcularSubtotal() {
        BigDecimal subtotalBase = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
        this.subtotal = subtotalBase.subtract(this.descuentoLinea);
    }
    
    void setId(Long id) {
        this.id = id;
    }
}