package com.joanleon.ordersystem.application.port.out;

import java.util.List;
import java.util.Optional;

import com.joanleon.ordersystem.domain.model.Pedido;

public interface PedidoRepositoryPort {
    Pedido save(Pedido pedido);
    Optional<Pedido> findById(Long id);
    List<Pedido> findAll();
    List<Pedido> findByClienteId(Long clienteId);
    void deleteById(Long id);
}
