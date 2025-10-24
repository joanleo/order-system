package com.joanleon.ordersystem.application.dto;

import com.joanleon.ordersystem.domain.model.Producto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Respuesta con los datos de un producto")
public class ProductoResponse {
    
    @Schema(description = "ID del producto", example = "1")
    private Long id;
    
    @Schema(description = "Código del producto", example = "PROD001")
    private Integer codigo;
    
    @Schema(description = "Nombre del producto", example = "Laptop Dell XPS 13")
    private String nombre;
    
    @Schema(description = "Descripción del producto", example = "Laptop ultradelgada 13 pulgadas")
    private String descripcion;
    
    @Schema(description = "Precio del producto", example = "1299.99")
    private BigDecimal precio;
    
    @Schema(description = "Cantidad en stock", example = "10")
    private Integer stock;
    
    public static ProductoResponse fromDomain(Producto producto) {
        return ProductoResponse.builder()
            .id(producto.getId())
            .codigo(producto.getCodigo())
            .nombre(producto.getNombre())
            .descripcion(producto.getDescripcion())
            .precio(producto.getPrecio())
            .stock(producto.getStock())
            .build();
    }
}
