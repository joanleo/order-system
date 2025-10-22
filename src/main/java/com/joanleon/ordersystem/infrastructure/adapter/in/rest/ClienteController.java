package com.joanleon.ordersystem.infrastructure.adapter.in.rest;

import com.joanleon.ordersystem.application.dto.ClienteRequest;
import com.joanleon.ordersystem.application.dto.ClienteResponse;
import com.joanleon.ordersystem.application.port.in.ClienteUseCase;
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
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "API para gestión de clientes")
public class ClienteController {

    private final ClienteUseCase clienteUseCase;

    @Operation(
            summary = "Obtener cliente por ID",
            description = "Retorna un cliente específico basado en su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cliente encontrado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ClienteResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente no encontrado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> obtenerPorId(
            @Parameter(description = "ID del cliente a buscar", required = true, example = "1")
            @PathVariable Long id) {
        ClienteResponse response = clienteUseCase.obtenerClientePorId(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Crear un nuevo cliente",
            description = "Crea un nuevo cliente en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Cliente creado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ClienteResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "El cliente ya existe",
                    content = @Content
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponse crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del cliente a crear",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ClienteRequest.class))
            )
            @Valid @RequestBody ClienteRequest request) {
        return clienteUseCase.crearCliente(request);
    }

    @Operation(
            summary = "Listar todos los clientes",
            description = "Retorna una lista con todos los clientes registrados en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de clientes obtenida exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ClienteResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    @GetMapping
    public ResponseEntity<List<ClienteResponse>> listar() {
        List<ClienteResponse> clientes = clienteUseCase.listar();
        return ResponseEntity.ok(clientes);
    }
    
    @Operation(
	    summary = "Actualizar un cliente existente",
	    description = "Actualiza los datos de un cliente existente en el sistema"
	)
	@ApiResponses(value = {
	    @ApiResponse(
	        responseCode = "200",
	        description = "Cliente actualizado exitosamente",
	        content = @Content(
	            mediaType = "application/json",
	            schema = @Schema(implementation = ClienteResponse.class)
	        )
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Datos de entrada inválidos",
	        content = @Content
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Cliente no encontrado",
	        content = @Content
	    )
	})
	@PutMapping("/{id}")
	public ResponseEntity<ClienteResponse> actualizar(
	    @Parameter(description = "ID del cliente a actualizar", required = true, example = "1")
	    @PathVariable Long id,
	    @io.swagger.v3.oas.annotations.parameters.RequestBody(
	        description = "Datos actualizados del cliente",
	        required = true,
	        content = @Content(schema = @Schema(implementation = ClienteRequest.class))
	    )
	    @Valid @RequestBody ClienteRequest request) {
	    ClienteResponse response = clienteUseCase.actualizarCliente(id, request);
	    return ResponseEntity.ok(response);
	}    
    
    @Operation(
	    summary = "Eliminar un cliente",
	    description = "Elimina un cliente del sistema basado en su ID"
	)
	@ApiResponses(value = {
	    @ApiResponse(
	        responseCode = "204",
	        description = "Cliente eliminado exitosamente",
	        content = @Content
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Cliente no encontrado",
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
	    @Parameter(description = "ID del cliente a eliminar", required = true, example = "1")
	    @PathVariable Long id) {
	    clienteUseCase.eliminarCliente(id);
	    return ResponseEntity.noContent().build();
	}

    

}