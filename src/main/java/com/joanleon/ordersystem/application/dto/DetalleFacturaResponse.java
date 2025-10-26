package com.joanleon.ordersystem.application.dto;

import com.joanleon.ordersystem.domain.model.DetalleFactura;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Detalle de un producto en la factura")
public class DetalleFacturaResponse {
    
    @Schema(description = "ID del detalle")
    private Long id;
    
    @Schema(description = "Nombre del producto")
    private String producto;
    
    @Schema(description = "Código del producto")
    private Integer codigoProducto;
    
    @Schema(description = "Cantidad")
    private Integer cantidad;
    
    @Schema(description = "Precio unitario")
    private BigDecimal precioUnitario;
    
    @Schema(description = "Descuento en esta línea")
    private BigDecimal descuentoLinea;
    
    @Schema(description = "Subtotal del detalle")
    private BigDecimal subtotal;
    
    public static DetalleFacturaResponse fromDomain(DetalleFactura detalle) {
        return DetalleFacturaResponse.builder()
            .id(detalle.getId())
            .producto(detalle.getProducto().getNombre())
            .codigoProducto(detalle.getProducto().getCodigo())
            .cantidad(detalle.getCantidad())
            .precioUnitario(detalle.getPrecioUnitario())
            .descuentoLinea(detalle.getDescuentoLinea())
            .subtotal(detalle.getSubtotal())
            .build();
    }
}