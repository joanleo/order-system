package com.joanleon.ordersystem.infrastructure.adapter.out;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.joanleon.ordersystem.application.port.out.EstadoRepositoryPort;
import com.joanleon.ordersystem.domain.model.Estado;
import com.joanleon.ordersystem.infrastructure.adapter.out.mapper.EstadoMapper;
import com.joanleon.ordersystem.infrastructure.adapter.out.repository.EstadoRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EstadoRepositoryAdapter implements EstadoRepositoryPort {

    private final EstadoRepository repository;
    private final EstadoMapper mapper;

    @Override
    public Optional<Estado> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Estado> findByTipoDocumentoId(Long tipoDocumentoId) {
        return repository.findByTipoDocumentoId(tipoDocumentoId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
