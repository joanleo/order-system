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
@Schema(description = "Detalle de un producto en la factura")
public class DetalleFacturaRequest {
    
    @NotNull(message = "El ID del producto es obligatorio")
    @Schema(description = "ID del producto", example = "1")
    private Long productoId;
    
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    @Schema(description = "Cantidad del producto", example = "2")
    private Integer cantidad;
    
    @DecimalMin(value = "0.0", message = "El descuento no puede ser negativo")
    @Schema(description = "Descuento aplicado a esta línea", example = "5.00")
    private BigDecimal descuentoLinea;
}