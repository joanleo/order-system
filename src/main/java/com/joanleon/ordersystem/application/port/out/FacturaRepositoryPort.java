package com.joanleon.ordersystem.application.port.out;

import com.joanleon.ordersystem.domain.model.Factura;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FacturaRepositoryPort {
    Factura save(Factura factura);
    Optional<Factura> findById(Long id);
    Optional<Factura> findByNumeroFactura(String numeroFactura);
    List<Factura> findAll();
    List<Factura> findByClienteId(Long clienteId);
    List<Factura> findByEstadoId(Long estadoId);
    List<Factura> findByFechaEmisionBetween(LocalDate fechaInicio, LocalDate fechaFin);
    List<Factura> findByClienteIdAndEstadoId(Long clienteId, Long estadoId);
    List<Factura> findBySaldoPendienteGreaterThan(BigDecimal monto);
    void deleteById(Long id);
    String generarNumeroFactura();
}