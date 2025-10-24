package com.joanleon.ordersystem.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class DetallePedido {
    private Long id;
    private Producto producto;
    private Integer cantidad;
    private BigDecimal precioUnitario;

    public DetallePedido(Producto producto, Integer cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecio();
    }

    public BigDecimal getSubtotal() {
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }
    
    void setId(Long id) {
        this.id = id;
    }
}
