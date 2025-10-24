package com.joanleon.ordersystem.infrastructure.adapter.out;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.joanleon.ordersystem.application.port.out.TipoDocumentoRepositoryPort;
import com.joanleon.ordersystem.domain.model.TipoDocumento;
import com.joanleon.ordersystem.infrastructure.adapter.out.mapper.TipoDocumentoMapper;
import com.joanleon.ordersystem.infrastructure.adapter.out.repository.TipoDocumentoRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TipoDocumentoRepositoryAdapter implements TipoDocumentoRepositoryPort {

    private final TipoDocumentoRepository repository;
    private final TipoDocumentoMapper mapper;

    @Override
    public Optional<TipoDocumento> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<TipoDocumento> findByCodigo(String codigo) {
        return repository.findByCodigo(codigo)
                .map(mapper::toDomain);
    }
}
