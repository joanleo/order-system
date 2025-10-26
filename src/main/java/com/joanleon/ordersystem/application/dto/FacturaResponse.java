package com.joanleon.ordersystem.application.dto;

import com.joanleon.ordersystem.domain.model.Factura;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Respuesta con los datos de una factura")
public class FacturaResponse {
    
    @Schema(description = "ID de la factura", example = "1")
    private Long id;
    
    @Schema(description = "Número de factura", example = "FACT-2025-0001")
    private String numeroFactura;
    
    @Schema(description = "Nombre del cliente")
    private String cliente;
    
    @Schema(description = "ID del cliente", example = "1")
    private Long clienteId;
    
    @Schema(description = "Número del pedido asociado (si existe)")
    private Long pedidoId;
    
    @Schema(description = "Estado de la factura")
    private String estado;
    
    @Schema(description = "Fecha de emisión")
    private LocalDate fechaEmision;
    
    @Schema(description = "Fecha de vencimiento")
    private LocalDate fechaVencimiento;
    
    @Schema(description = "Fecha de creación")
    private LocalDateTime fechaCreacion;
    
    @Schema(description = "Subtotal antes de descuentos e impuestos")
    private BigDecimal subtotal;
    
    @Schema(description = "Descuento aplicado")
    private BigDecimal descuento;
    
    @Schema(description = "IVA calculado")
    private BigDecimal iva;
    
    @Schema(description = "Total de la factura")
    private BigDecimal total;
    
    @Schema(description = "Método de pago")
    private String metodoPago;
    
    @Schema(description = "Monto pagado hasta el momento")
    private BigDecimal montoPagado;
    
    @Schema(description = "Saldo pendiente por pagar")
    private BigDecimal saldoPendiente;
    
    @Schema(description = "Indica si está pagada completamente")
    private Boolean pagada;
    
    @Schema(description = "Indica si está parcialmente pagada")
    private Boolean parcialmentePagada;
    
    @Schema(description = "Términos y condiciones")
    private String terminosCondiciones;
    
    @Schema(description = "Observaciones")
    private String observaciones;
    
    @Schema(description = "Detalles de la factura")
    private List<DetalleFacturaResponse> detalles;
    
    public static FacturaResponse fromDomain(Factura factura) {
        return FacturaResponse.builder()
            .id(factura.getId())
            .numeroFactura(factura.getNumeroFactura())
            .cliente(factura.getCliente().getNombre())
            .clienteId(factura.getCliente().getId())
            .pedidoId(factura.getPedido() != null ? factura.getPedido().getId() : null)
            .estado(factura.getEstado().getDescripcion())
            .fechaEmision(factura.getFechaEmision())
            .fechaVencimiento(factura.getFechaVencimiento())
            .fechaCreacion(factura.getFechaCreacion())
            .subtotal(factura.getSubtotal())
            .descuento(factura.getDescuento())
            .iva(factura.getIva())
            .total(factura.getTotal())
            .metodoPago(factura.getMetodoPago())
            .montoPagado(factura.getMontoPagado())
            .saldoPendiente(factura.getSaldoPendiente())
            .pagada(factura.estaPagada())
            .parcialmentePagada(factura.estaParcialmentePagada())
            .terminosCondiciones(factura.getTerminosCondiciones())
            .observaciones(factura.getObservaciones())
            .detalles(factura.getDetalles().stream()
                .map(DetalleFacturaResponse::fromDomain)
                .toList())
            .build();
    }
}