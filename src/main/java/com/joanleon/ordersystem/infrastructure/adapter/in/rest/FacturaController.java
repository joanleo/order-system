package com.joanleon.ordersystem.infrastructure.adapter.in.rest;

import com.joanleon.ordersystem.application.dto.*;
import com.joanleon.ordersystem.application.port.in.FacturaUseCase;
import com.joanleon.ordersystem.application.service.FacturaPdfService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/facturas")
@RequiredArgsConstructor
@Tag(name = "Facturas", description = "API para gestión de facturas")
public class FacturaController {

    private final FacturaUseCase facturaUseCase;
    private final FacturaPdfService pdfService;

    @Operation(
        summary = "Crear factura desde pedido",
        description = "Genera una factura a partir de un pedido existente en estado 'Entregado'"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Factura creada exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = FacturaResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos o pedido no cumple requisitos",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Pedido no encontrado",
            content = @Content
        )
    })
    @PostMapping("/desde-pedido")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<FacturaResponse> crearDesdePedido(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos de la factura (debe incluir pedidoId)",
            required = true,
            content = @Content(schema = @Schema(implementation = FacturaRequest.class))
        )
        @Valid @RequestBody FacturaRequest request) {
        FacturaResponse response = facturaUseCase.crearFacturaDesdePedido(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
        summary = "Crear factura manual",
        description = "Crea una factura directamente sin pedido previo"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Factura creada exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = FacturaResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos",
            content = @Content
        )
    })
    @PostMapping("/manual")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<FacturaResponse> crearManual(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos de la factura (debe incluir detalles)",
            required = true,
            content = @Content(schema = @Schema(implementation = FacturaRequest.class))
        )
        @Valid @RequestBody FacturaRequest request) {
        FacturaResponse response = facturaUseCase.crearFacturaManual(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
        summary = "Obtener factura por ID",
        description = "Retorna una factura específica basada en su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Factura encontrada",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = FacturaResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Factura no encontrada",
            content = @Content
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<FacturaResponse> obtenerPorId(
        @Parameter(description = "ID de la factura", required = true, example = "1")
        @PathVariable Long id) {
        FacturaResponse response = facturaUseCase.obtenerFacturaPorId(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Obtener factura por número",
        description = "Busca una factura por su número único"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Factura encontrada",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = FacturaResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Factura no encontrada",
            content = @Content
        )
    })
    @GetMapping("/numero/{numeroFactura}")
    public ResponseEntity<FacturaResponse> obtenerPorNumero(
        @Parameter(description = "Número de factura", required = true, example = "FACT-2025-0001")
        @PathVariable String numeroFactura) {
        FacturaResponse response = facturaUseCase.obtenerFacturaPorNumero(numeroFactura);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Listar todas las facturas",
        description = "Retorna una lista con todas las facturas del sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de facturas obtenida",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = FacturaResponse.class)
            )
        )
    })
    @GetMapping
    public ResponseEntity<List<FacturaResponse>> listarTodas() {
        List<FacturaResponse> facturas = facturaUseCase.listarTodas();
        return ResponseEntity.ok(facturas);
    }

    @Operation(
        summary = "Listar facturas por cliente",
        description = "Retorna todas las facturas de un cliente específico"
    )
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<FacturaResponse>> listarPorCliente(
        @Parameter(description = "ID del cliente", required = true, example = "1")
        @PathVariable Long clienteId) {
        List<FacturaResponse> facturas = facturaUseCase.listarPorCliente(clienteId);
        return ResponseEntity.ok(facturas);
    }

    @Operation(
        summary = "Listar facturas por estado",
        description = "Retorna facturas filtradas por estado"
    )
    @GetMapping("/estado/{estadoId}")
    public ResponseEntity<List<FacturaResponse>> listarPorEstado(
        @Parameter(description = "ID del estado", required = true, example = "1")
        @PathVariable Long estadoId) {
        List<FacturaResponse> facturas = facturaUseCase.listarPorEstado(estadoId);
        return ResponseEntity.ok(facturas);
    }

    @Operation(
        summary = "Listar facturas por rango de fechas",
        description = "Retorna facturas emitidas entre dos fechas"
    )
    @GetMapping("/rango-fechas")
    public ResponseEntity<List<FacturaResponse>> listarPorRangoFechas(
        @Parameter(description = "Fecha inicio", required = true, example = "2025-01-01")
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
        @Parameter(description = "Fecha fin", required = true, example = "2025-12-31")
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        List<FacturaResponse> facturas = facturaUseCase.listarPorRangoFechas(fechaInicio, fechaFin);
        return ResponseEntity.ok(facturas);
    }

    @Operation(
        summary = "Listar facturas pendientes por cliente",
        description = "Retorna facturas con saldo pendiente de un cliente"
    )
    @GetMapping("/pendientes/cliente/{clienteId}")
    public ResponseEntity<List<FacturaResponse>> listarPendientesPorCliente(
        @Parameter(description = "ID del cliente", required = true, example = "1")
        @PathVariable Long clienteId) {
        List<FacturaResponse> facturas = facturaUseCase.listarFacturasPendientesPorCliente(clienteId);
        return ResponseEntity.ok(facturas);
    }

    @Operation(
        summary = "Listar facturas vencidas",
        description = "Retorna facturas con fecha de vencimiento pasada y saldo pendiente"
    )
    @GetMapping("/vencidas")
    public ResponseEntity<List<FacturaResponse>> listarVencidas() {
        List<FacturaResponse> facturas = facturaUseCase.listarFacturasVencidas();
        return ResponseEntity.ok(facturas);
    }

    @Operation(
        summary = "Cambiar estado de factura",
        description = "Actualiza el estado de una factura"
    )
    @PatchMapping("/{id}/estado")
    public ResponseEntity<FacturaResponse> cambiarEstado(
        @Parameter(description = "ID de la factura", required = true, example = "1")
        @PathVariable Long id,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Nuevo estado",
            required = true,
            content = @Content(schema = @Schema(implementation = CambiarEstadoRequest.class))
        )
        @Valid @RequestBody CambiarEstadoRequest request) {
        FacturaResponse response = facturaUseCase.cambiarEstado(id, request.getEstadoId());
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Anular factura",
        description = "Cambia el estado de la factura a 'Anulada'"
    )
    @PatchMapping("/{id}/anular")
    public ResponseEntity<FacturaResponse> anular(
        @Parameter(description = "ID de la factura", required = true, example = "1")
        @PathVariable Long id) {
        FacturaResponse response = facturaUseCase.anularFactura(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Eliminar factura",
        description = "Elimina una factura (solo si está anulada y sin pagos)"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
        @Parameter(description = "ID de la factura", required = true, example = "1")
        @PathVariable Long id) {
        facturaUseCase.eliminarFactura(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Registrar pago",
        description = "Registra un pago para una factura"
    )
    @PostMapping("/pagos")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PagoResponse> registrarPago(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del pago",
            required = true,
            content = @Content(schema = @Schema(implementation = PagoRequest.class))
        )
        @Valid @RequestBody PagoRequest request) {
        PagoResponse response = facturaUseCase.registrarPago(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
        summary = "Listar pagos por factura",
        description = "Retorna todos los pagos de una factura"
    )
    @GetMapping("/{facturaId}/pagos")
    public ResponseEntity<List<PagoResponse>> listarPagosPorFactura(
        @Parameter(description = "ID de la factura", required = true, example = "1")
        @PathVariable Long facturaId) {
        List<PagoResponse> pagos = facturaUseCase.listarPagosPorFactura(facturaId);
        return ResponseEntity.ok(pagos);
    }

    @Operation(
        summary = "Listar pagos por cliente",
        description = "Retorna todos los pagos realizados por un cliente"
    )
    @GetMapping("/pagos/cliente/{clienteId}")
    public ResponseEntity<List<PagoResponse>> listarPagosPorCliente(
        @Parameter(description = "ID del cliente", required = true, example = "1")
        @PathVariable Long clienteId) {
        List<PagoResponse> pagos = facturaUseCase.listarPagosPorCliente(clienteId);
        return ResponseEntity.ok(pagos);
    }

    @Operation(
        summary = "Obtener pago por ID",
        description = "Retorna un pago específico"
    )
    @GetMapping("/pagos/{id}")
    public ResponseEntity<PagoResponse> obtenerPagoPorId(
        @Parameter(description = "ID del pago", required = true, example = "1")
        @PathVariable Long id) {
        PagoResponse response = facturaUseCase.obtenerPagoPorId(id);
        return ResponseEntity.ok(response);
    }
    
    @Operation(
        summary = "Descargar factura en PDF",
        description = "Genera y descarga una factura en formato PDF"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "PDF generado exitosamente"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Factura no encontrada",
            content = @Content
        )
    })
    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> descargarPdf(
        @Parameter(description = "ID de la factura", required = true, example = "1")
        @PathVariable Long id) {
        
        byte[] pdf = pdfService.generarPdf(id);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "factura-" + id + ".pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        
        return ResponseEntity.ok()
            .headers(headers)
            .body(pdf);
    }
    
    @Operation(
	    summary = "Obtener estadísticas de facturación",
	    description = "Retorna estadísticas completas de facturación para un año específico"
	)
	@ApiResponses(value = {
	    @ApiResponse(
	        responseCode = "200",
	        description = "Estadísticas obtenidas exitosamente",
	        content = @Content(
	            mediaType = "application/json",
	            schema = @Schema(implementation = EstadisticasFacturacion.class)
	        )
	    )
	})
	@GetMapping("/estadisticas")
	public ResponseEntity<EstadisticasFacturacion> obtenerEstadisticas(
	    @Parameter(description = "Año para las estadísticas", example = "2025")
	    @RequestParam(defaultValue = "2025") int anio) {
	    EstadisticasFacturacion estadisticas = facturaUseCase.obtenerEstadisticas(anio);
	    return ResponseEntity.ok(estadisticas);
	}
}