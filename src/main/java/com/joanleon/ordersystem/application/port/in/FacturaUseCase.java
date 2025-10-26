package com.joanleon.ordersystem.application.port.in;

import com.joanleon.ordersystem.application.dto.EstadisticasFacturacion;
import com.joanleon.ordersystem.application.dto.FacturaRequest;
import com.joanleon.ordersystem.application.dto.FacturaResponse;
import com.joanleon.ordersystem.application.dto.PagoRequest;
import com.joanleon.ordersystem.application.dto.PagoResponse;

import java.time.LocalDate;
import java.util.List;

public interface FacturaUseCase {
    // Gestión de facturas
    FacturaResponse crearFacturaDesdePedido(FacturaRequest request);
    FacturaResponse crearFacturaManual(FacturaRequest request);
    FacturaResponse obtenerFacturaPorId(Long id);
    FacturaResponse obtenerFacturaPorNumero(String numeroFactura);
    List<FacturaResponse> listarTodas();
    List<FacturaResponse> listarPorCliente(Long clienteId);
    List<FacturaResponse> listarPorEstado(Long estadoId);
    List<FacturaResponse> listarPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin);
    List<FacturaResponse> listarFacturasPendientesPorCliente(Long clienteId);
    List<FacturaResponse> listarFacturasVencidas();
    FacturaResponse cambiarEstado(Long facturaId, Long estadoId);
    FacturaResponse anularFactura(Long facturaId);
    void eliminarFactura(Long id);
    
    // Gestión de pagos
    PagoResponse registrarPago(PagoRequest request);
    List<PagoResponse> listarPagosPorFactura(Long facturaId);
    List<PagoResponse> listarPagosPorCliente(Long clienteId);
    PagoResponse obtenerPagoPorId(Long id);
    
    EstadisticasFacturacion obtenerEstadisticas(int anio);
}