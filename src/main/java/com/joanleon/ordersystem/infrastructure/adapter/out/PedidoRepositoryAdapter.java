package com.joanleon.ordersystem.infrastructure.adapter.out;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.joanleon.ordersystem.application.port.out.PedidoRepositoryPort;
import com.joanleon.ordersystem.domain.model.Pedido;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PedidoRepositoryAdapter implements PedidoRepositoryPort {

    // Aquí inyectarías tu JpaRepository si lo tienes
    // private final PedidoJpaRepository jpaRepository;

    @Override
    public Pedido save(Pedido pedido) {
        // Implementa la lógica de guardado
        // Ejemplo: convertir a entidad JPA, guardar, y convertir de vuelta
        throw new UnsupportedOperationException("Implementar guardado");
    }

    @Override
    public Optional<Pedido> findById(Long id) {
        // Implementa la lógica de búsqueda
        throw new UnsupportedOperationException("Implementar búsqueda");
    }
}
