package com.joanleon.ordersystem.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Estadísticas generales de facturación")
public class EstadisticasFacturacion {
    
    @Schema(description = "Total facturado en el período", example = "150000.00")
    private BigDecimal totalFacturado;
    
    @Schema(description = "Total pagado", example = "120000.00")
    private BigDecimal totalPagado;
    
    @Schema(description = "Total pendiente de pago", example = "30000.00")
    private BigDecimal totalPendiente;
    
    @Schema(description = "Número total de facturas", example = "45")
    private Long totalFacturas;
    
    @Schema(description = "Número de facturas vencidas", example = "5")
    private Long facturasVencidas;
    
    @Schema(description = "Número de facturas pagadas", example = "35")
    private Long facturasPagadas;
    
    @Schema(description = "Número de facturas pendientes", example = "8")
    private Long facturasPendientes;
    
    @Schema(description = "Ventas por mes del año (key: mes, value: total)")
    private Map<String, BigDecimal> ventasPorMes;
    
    @Schema(description = "Top 5 clientes por monto de compras")
    private List<ClienteTopDTO> topClientes;
    
    @Schema(description = "Distribución de facturas por estado")
    private Map<String, Long> facturasPorEstado;
}