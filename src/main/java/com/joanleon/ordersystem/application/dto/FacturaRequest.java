package com.joanleon.ordersystem.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Datos para crear una factura")
public class FacturaRequest {
    
    @Schema(description = "ID del pedido (opcional, si se factura desde un pedido)")
    private Long pedidoId;
    
    @NotNull(message = "El ID del cliente es obligatorio")
    @Schema(description = "ID del cliente", example = "1")
    private Long clienteId;
    
    @Schema(description = "Detalles de la factura (si no viene de pedido)")
    @Valid
    private List<DetalleFacturaRequest> detalles;
    
    @NotNull(message = "La fecha de emisión es obligatoria")
    @Schema(description = "Fecha de emisión", example = "2025-01-15")
    private LocalDate fechaEmision;
    
    @NotNull(message = "La fecha de vencimiento es obligatoria")
    @Schema(description = "Fecha de vencimiento", example = "2025-02-15")
    private LocalDate fechaVencimiento;
    
    @DecimalMin(value = "0.0", message = "El descuento no puede ser negativo")
    @Schema(description = "Descuento aplicado", example = "10.00")
    private BigDecimal descuento;
    
    @DecimalMin(value = "0.0", message = "La tasa de IVA no puede ser negativa")
    @DecimalMax(value = "1.0", message = "La tasa de IVA no puede ser mayor a 1")
    @Schema(description = "Tasa de IVA (0.19 = 19%)", example = "0.19")
    private BigDecimal tasaIva;
    
    @NotBlank(message = "El método de pago es obligatorio")
    @Schema(description = "Método de pago", example = "EFECTIVO")
    private String metodoPago;
    
    @Schema(description = "Términos y condiciones")
    private String terminosCondiciones;
    
    @Schema(description = "Observaciones adicionales")
    private String observaciones;
}