package com.joanleon.ordersystem.application.port.out;

import java.util.Optional;

import com.joanleon.ordersystem.domain.model.Pedido;

public interface PedidoRepositoryPort {
    Pedido save(Pedido pedido);
    Optional<Pedido> findById(Long id);
}
