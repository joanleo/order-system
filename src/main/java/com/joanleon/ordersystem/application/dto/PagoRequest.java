package com.joanleon.ordersystem.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Datos para registrar un pago")
public class PagoRequest {
    
    @NotNull(message = "El ID de la factura es obligatorio")
    @Schema(description = "ID de la factura a pagar", example = "1")
    private Long facturaId;
    
    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
    @Schema(description = "Monto del pago", example = "100.00")
    private BigDecimal monto;
    
    @NotBlank(message = "El método de pago es obligatorio")
    @Schema(description = "Método de pago", example = "TRANSFERENCIA")
    private String metodoPago;
    
    @Schema(description = "Referencia del pago", example = "REF-12345")
    private String referencia;
    
    @Schema(description = "Observaciones del pago")
    private String observaciones;
}