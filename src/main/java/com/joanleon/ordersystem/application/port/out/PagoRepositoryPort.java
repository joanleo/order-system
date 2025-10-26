package com.joanleon.ordersystem.application.port.out;

import com.joanleon.ordersystem.domain.model.Pago;

import java.util.List;
import java.util.Optional;

public interface PagoRepositoryPort {
    Pago save(Pago pago);
    Optional<Pago> findById(Long id);
    List<Pago> findByFacturaId(Long facturaId);
    List<Pago> findByFacturaClienteId(Long clienteId);
    List<Pago> findAll();
}