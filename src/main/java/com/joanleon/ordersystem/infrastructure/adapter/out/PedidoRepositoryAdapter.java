package com.joanleon.ordersystem.infrastructure.adapter.out;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.joanleon.ordersystem.application.port.out.PedidoRepositoryPort;
import com.joanleon.ordersystem.domain.model.Pedido;
import com.joanleon.ordersystem.infrastructure.adapter.out.mapper.PedidoMapper;
import com.joanleon.ordersystem.infrastructure.adapter.out.repository.PedidoRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PedidoRepositoryAdapter implements PedidoRepositoryPort {

    private final PedidoRepository repository;
    private final PedidoMapper mapper;

    @Override
    public Pedido save(Pedido pedido) {
        var pedidoEntity = mapper.toEntity(pedido);
        var pedidoGuardado = repository.save(pedidoEntity);
        return mapper.toDomain(pedidoGuardado);
    }

    @Override
    public Optional<Pedido> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Pedido> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Pedido> findByClienteId(Long clienteId) {
        return repository.findByClienteId(clienteId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
