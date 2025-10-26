package com.joanleon.ordersystem.application.dto;

import com.joanleon.ordersystem.domain.model.Pago;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Respuesta con los datos de un pago")
public class PagoResponse {
    
    @Schema(description = "ID del pago")
    private Long id;
    
    @Schema(description = "Número de factura")
    private String numeroFactura;
    
    @Schema(description = "ID de la factura")
    private Long facturaId;
    
    @Schema(description = "Monto del pago")
    private BigDecimal monto;
    
    @Schema(description = "Método de pago")
    private String metodoPago;
    
    @Schema(description = "Referencia del pago")
    private String referencia;
    
    @Schema(description = "Fecha del pago")
    private LocalDateTime fechaPago;
    
    @Schema(description = "Observaciones")
    private String observaciones;
    
    public static PagoResponse fromDomain(Pago pago) {
        return PagoResponse.builder()
            .id(pago.getId())
            .numeroFactura(pago.getFactura().getNumeroFactura())
            .facturaId(pago.getFactura().getId())
            .monto(pago.getMonto())
            .metodoPago(pago.getMetodoPago())
            .referencia(pago.getReferencia())
            .fechaPago(pago.getFechaPago())
            .observaciones(pago.getObservaciones())
            .build();
    }
}