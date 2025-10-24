package com.joanleon.ordersystem.infrastructure.adapter.out;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.joanleon.ordersystem.application.port.out.ProductoRepositoryPort;
import com.joanleon.ordersystem.domain.model.Producto;
import com.joanleon.ordersystem.infrastructure.adapter.out.mapper.ProductoMapper;
import com.joanleon.ordersystem.infrastructure.adapter.out.repository.ProductoRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductoRepositoryAdapter implements ProductoRepositoryPort {

    private final ProductoRepository repository;
    private final ProductoMapper mapper;

    @Override
    public Optional<Producto> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Producto> findByCodigo(Integer codigo) {
        return repository.findByCodigo(codigo)
                .map(mapper::toDomain);
    }

    @Override
    public List<Producto> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Producto save(Producto producto) {
        var entity = mapper.toEntity(producto);
        var savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}