// infrastructure/adapter/in/rest/ProductoController.java
package com.joanleon.ordersystem.infrastructure.adapter.in.rest;

import com.joanleon.ordersystem.application.dto.ProductoRequest;
import com.joanleon.ordersystem.application.dto.ProductoResponse;
import com.joanleon.ordersystem.application.port.in.ProductoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "API para gestión de productos")
public class ProductoController {

    private final ProductoUseCase productoUseCase;

    @Operation(
            summary = "Obtener producto por ID",
            description = "Retorna un producto específico basado en su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Producto encontrado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductoResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Producto no encontrado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> obtenerPorId(
            @Parameter(description = "ID del producto a buscar", required = true, example = "1")
            @PathVariable Long id) {
        ProductoResponse response = productoUseCase.obtenerProductoPorId(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Crear un nuevo producto",
            description = "Crea un nuevo producto en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Producto creado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductoResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "El producto ya existe",
                    content = @Content
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductoResponse crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del producto a crear",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ProductoRequest.class))
            )
            @Valid @RequestBody ProductoRequest request) {
        return productoUseCase.crearProducto(request);
    }

    @Operation(
            summary = "Listar todos los productos",
            description = "Retorna una lista con todos los productos registrados en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos obtenida exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductoResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    @GetMapping
    public ResponseEntity<List<ProductoResponse>> listar() {
        List<ProductoResponse> productos = productoUseCase.listar();
        return ResponseEntity.ok(productos);
    }
    
    @Operation(
            summary = "Actualizar un producto existente",
            description = "Actualiza los datos de un producto existente en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Producto actualizado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductoResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Producto no encontrado",
                    content = @Content
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> actualizar(
            @Parameter(description = "ID del producto a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos actualizados del producto",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ProductoRequest.class))
            )
            @Valid @RequestBody ProductoRequest request) {
        ProductoResponse response = productoUseCase.actualizarProducto(id, request);
        return ResponseEntity.ok(response);
    }    
    
    @Operation(
            summary = "Eliminar un producto",
            description = "Elimina un producto del sistema basado en su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Producto eliminado exitosamente",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Producto no encontrado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del producto a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        productoUseCase.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(
            summary = "Buscar producto por código",
            description = "Retorna un producto específico basado en su código"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Producto encontrado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductoResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Producto no encontrado",
                    content = @Content
            )
    })
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<ProductoResponse> obtenerPorCodigo(
            @Parameter(description = "Código del producto a buscar", required = true, example = "PROD001")
            @PathVariable Integer codigo) {
        ProductoResponse response = productoUseCase.obtenerProductoPorCodigo(codigo);
        return ResponseEntity.ok(response);
    }
}