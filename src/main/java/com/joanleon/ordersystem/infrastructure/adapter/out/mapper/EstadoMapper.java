package com.joanleon.ordersystem.infrastructure.adapter.out.mapper;

import org.springframework.stereotype.Component;

import com.joanleon.ordersystem.domain.model.Estado;
import com.joanleon.ordersystem.infrastructure.adapter.out.entity.EstadoEntity;

@Component
public class EstadoMapper {
    
    private final TipoDocumentoMapper tipoDocumentoMapper;
    
    public EstadoMapper(TipoDocumentoMapper tipoDocumentoMapper) {
        this.tipoDocumentoMapper = tipoDocumentoMapper;
    }
    
    public Estado toDomain(EstadoEntity entity) {
        if (entity == null) return null;
        return new Estado(
            entity.getId(),
            entity.getDescripcion(),
            tipoDocumentoMapper.toDomain(entity.getTipoDocumento())
        );
    }
    
    public EstadoEntity toEntity(Estado domain) {
        if (domain == null) return null;
        return EstadoEntity.builder()
            .id(domain.getId())
            .descripcion(domain.getDescripcion())
            .tipoDocumento(tipoDocumentoMapper.toEntity(domain.getTipoDocumento()))
            .build();
    }
}
