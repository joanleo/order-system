package com.joanleon.ordersystem.infrastructure.adapter.out.mapper;

import org.springframework.stereotype.Component;

import com.joanleon.ordersystem.domain.model.TipoDocumento;
import com.joanleon.ordersystem.infrastructure.adapter.out.entity.TipoDocumentoEntity;

@Component
public class TipoDocumentoMapper {
    
    public TipoDocumento toDomain(TipoDocumentoEntity entity) {
        if (entity == null) return null;
        return new TipoDocumento(
            entity.getId(),
            entity.getCodigo(),
            entity.getDescripcion()
        );
    }
    
    public TipoDocumentoEntity toEntity(TipoDocumento domain) {
        if (domain == null) return null;
        return TipoDocumentoEntity.builder()
            .id(domain.getId())
            .codigo(domain.getCodigo())
            .descripcion(domain.getDescripcion())
            .build();
    }
}
