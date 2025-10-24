package com.joanleon.ordersystem.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

import com.joanleon.ordersystem.domain.model.DetallePedido;

@Data
@AllArgsConstructor
@Builder
public class DetallePedidoResponse {
    private Long id;
    private String producto;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
    
    public static DetallePedidoResponse fromDomain(DetallePedido detalle) {
        return DetallePedidoResponse.builder()
            .id(detalle.getId())
            .producto(detalle.getProducto().getNombre())
            .cantidad(detalle.getCantidad())
            .precioUnitario(detalle.getPrecioUnitario())
            .subtotal(detalle.getSubtotal())
            .build();
    }
}
