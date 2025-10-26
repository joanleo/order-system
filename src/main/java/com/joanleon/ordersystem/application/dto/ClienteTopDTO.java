package com.joanleon.ordersystem.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Cliente con mayor volumen de compras")
public class ClienteTopDTO {
    
    @Schema(description = "ID del cliente", example = "1")
    private Long id;
    
    @Schema(description = "Nombre del cliente", example = "Juan Pérez")
    private String nombre;
    
    @Schema(description = "Total de compras", example = "25000.00")
    private BigDecimal totalCompras;
    
    @Schema(description = "Cantidad de facturas", example = "12")
    private Long cantidadFacturas;
}