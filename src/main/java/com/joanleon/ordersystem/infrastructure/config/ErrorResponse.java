package com.joanleon.ordersystem.infrastructure.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Respuesta de error estándar")
public class ErrorResponse {
    
    @Schema(description = "Código de estado HTTP", example = "400")
    private int status;
    
    @Schema(description = "Mensaje de error", example = "El cliente no fue encontrado")
    private String message;
    
    @Schema(description = "Errores de validación por campo")
    private Map<String, String> errors;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Timestamp del error", example = "2025-01-15 10:30:00")
    private LocalDateTime timestamp;
    
    @Schema(description = "Ruta del endpoint", example = "/api/clientes/999")
    private String path;
    
    // Constructor simplificado para errores sin campos
    public ErrorResponse(int status, String message, LocalDateTime timestamp, String path) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
        this.path = path;
    }
}