package com.joanleon.ordersystem.infrastructure.adapter.in.rest;

import com.joanleon.ordersystem.application.dto.PedidoRequest;
import com.joanleon.ordersystem.application.dto.PedidoResponse;
import com.joanleon.ordersystem.application.dto.CambiarEstadoRequest;
import com.joanleon.ordersystem.application.port.in.PedidoUseCase;
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
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "API para gestión de pedidos")
public class PedidoController {

 private final PedidoUseCase pedidoUseCase;

 @Operation(
         summary = "Crear un nuevo pedido",
         description = "Crea un nuevo pedido en el sistema con estado inicial 'Pendiente'"
 )
 @ApiResponses(value = {
         @ApiResponse(
                 responseCode = "201",
                 description = "Pedido creado exitosamente",
                 content = @Content(
                         mediaType = "application/json",
                         schema = @Schema(implementation = PedidoResponse.class)
                 )
         ),
         @ApiResponse(
                 responseCode = "400",
                 description = "Datos de entrada inválidos",
                 content = @Content
         ),
         @ApiResponse(
                 responseCode = "404",
                 description = "Cliente o producto no encontrado",
                 content = @Content
         )
 })
 @PostMapping
 @ResponseStatus(HttpStatus.CREATED)
 public ResponseEntity<PedidoResponse> crearPedido(
         @io.swagger.v3.oas.annotations.parameters.RequestBody(
                 description = "Datos del pedido a crear",
                 required = true,
                 content = @Content(schema = @Schema(implementation = PedidoRequest.class))
         )
         @Valid @RequestBody PedidoRequest request) {
     PedidoResponse response = pedidoUseCase.crearPedido(request);
     return ResponseEntity.status(HttpStatus.CREATED).body(response);
 }

 @Operation(
         summary = "Obtener pedido por ID",
         description = "Retorna un pedido específico basado en su ID"
 )
 @ApiResponses(value = {
         @ApiResponse(
                 responseCode = "200",
                 description = "Pedido encontrado exitosamente",
                 content = @Content(
                         mediaType = "application/json",
                         schema = @Schema(implementation = PedidoResponse.class)
                 )
         ),
         @ApiResponse(
                 responseCode = "404",
                 description = "Pedido no encontrado",
                 content = @Content
         )
 })
 @GetMapping("/{id}")
 public ResponseEntity<PedidoResponse> obtenerPorId(
         @Parameter(description = "ID del pedido a buscar", required = true, example = "1")
         @PathVariable Long id) {
     PedidoResponse response = pedidoUseCase.obtenerPedidoPorId(id);
     return ResponseEntity.ok(response);
 }

 @Operation(
         summary = "Listar todos los pedidos",
         description = "Retorna una lista con todos los pedidos registrados en el sistema"
 )
 @ApiResponses(value = {
         @ApiResponse(
                 responseCode = "200",
                 description = "Lista de pedidos obtenida exitosamente",
                 content = @Content(
                         mediaType = "application/json",
                         schema = @Schema(implementation = PedidoResponse.class)
                 )
         )
 })
 @GetMapping
 public ResponseEntity<List<PedidoResponse>> listar() {
     List<PedidoResponse> pedidos = pedidoUseCase.listar();
     return ResponseEntity.ok(pedidos);
 }

 @Operation(
         summary = "Listar pedidos por cliente",
         description = "Retorna una lista de pedidos de un cliente específico"
 )
 @ApiResponses(value = {
         @ApiResponse(
                 responseCode = "200",
                 description = "Lista de pedidos del cliente obtenida exitosamente",
                 content = @Content(
                         mediaType = "application/json",
                         schema = @Schema(implementation = PedidoResponse.class)
                 )
         ),
         @ApiResponse(
                 responseCode = "404",
                 description = "Cliente no encontrado",
                 content = @Content
         )
 })
 @GetMapping("/cliente/{clienteId}")
 public ResponseEntity<List<PedidoResponse>> listarPorCliente(
         @Parameter(description = "ID del cliente", required = true, example = "1")
         @PathVariable Long clienteId) {
     List<PedidoResponse> pedidos = pedidoUseCase.listarPorCliente(clienteId);
     return ResponseEntity.ok(pedidos);
 }

 @Operation(
         summary = "Cambiar estado del pedido",
         description = "Actualiza el estado de un pedido existente"
 )
 @ApiResponses(value = {
         @ApiResponse(
                 responseCode = "200",
                 description = "Estado del pedido actualizado exitosamente",
                 content = @Content(
                         mediaType = "application/json",
                         schema = @Schema(implementation = PedidoResponse.class)
                 )
         ),
         @ApiResponse(
                 responseCode = "400",
                 description = "Estado inválido o no pertenece al tipo de documento",
                 content = @Content
         ),
         @ApiResponse(
                 responseCode = "404",
                 description = "Pedido o estado no encontrado",
                 content = @Content
         )
 })
 @PatchMapping("/{id}/estado")
 public ResponseEntity<PedidoResponse> cambiarEstado(
         @Parameter(description = "ID del pedido", required = true, example = "1")
         @PathVariable Long id,
         @io.swagger.v3.oas.annotations.parameters.RequestBody(
                 description = "Datos para cambiar el estado",
                 required = true,
                 content = @Content(schema = @Schema(implementation = CambiarEstadoRequest.class))
         )
         @Valid @RequestBody CambiarEstadoRequest request) {
     PedidoResponse response = pedidoUseCase.cambiarEstado(id, request.getEstadoId());
     return ResponseEntity.ok(response);
 }

 @Operation(
         summary = "Cancelar un pedido",
         description = "Cambia el estado del pedido a 'Cancelado'"
 )
 @ApiResponses(value = {
         @ApiResponse(
                 responseCode = "200",
                 description = "Pedido cancelado exitosamente",
                 content = @Content(
                         mediaType = "application/json",
                         schema = @Schema(implementation = PedidoResponse.class)
                 )
         ),
         @ApiResponse(
                 responseCode = "404",
                 description = "Pedido no encontrado",
                 content = @Content
         ),
         @ApiResponse(
                 responseCode = "400",
                 description = "El pedido no puede ser cancelado",
                 content = @Content
         )
 })
 @PatchMapping("/{id}/cancelar")
 public ResponseEntity<PedidoResponse> cancelar(
         @Parameter(description = "ID del pedido a cancelar", required = true, example = "1")
         @PathVariable Long id) {
     PedidoResponse response = pedidoUseCase.cancelarPedido(id);
     return ResponseEntity.ok(response);
 }

 @Operation(
         summary = "Eliminar un pedido",
         description = "Elimina un pedido del sistema (solo si está en estado Pendiente o Cancelado)"
 )
 @ApiResponses(value = {
         @ApiResponse(
                 responseCode = "204",
                 description = "Pedido eliminado exitosamente",
                 content = @Content
         ),
         @ApiResponse(
                 responseCode = "404",
                 description = "Pedido no encontrado",
                 content = @Content
         ),
         @ApiResponse(
                 responseCode = "400",
                 description = "El pedido no puede ser eliminado en su estado actual",
                 content = @Content
         )
 })
 @DeleteMapping("/{id}")
 public ResponseEntity<Void> eliminar(
         @Parameter(description = "ID del pedido a eliminar", required = true, example = "1")
         @PathVariable Long id) {
     pedidoUseCase.eliminarPedido(id);
     return ResponseEntity.noContent().build();
 }
}