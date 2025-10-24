package com.joanleon.ordersystem.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Datos para crear un pedido")
public class PedidoRequest {
    
    @NotNull(message = "El ID del cliente es obligatorio")
    @Schema(description = "ID del cliente que realiza el pedido", example = "1")
    private Long clienteId;
    
    @NotEmpty(message = "El pedido debe tener al menos un detalle")
    @Valid
    @Schema(description = "Lista de productos del pedido")
    private List<DetallePedidoRequest> detalles;
}
