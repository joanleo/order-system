package com.joanleon.ordersystem.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Datos para cambiar el estado de un pedido")
public class CambiarEstadoRequest {
    
    @NotNull(message = "El ID del estado es obligatorio")
    @Schema(description = "ID del nuevo estado", example = "2")
    private Long estadoId;
}
