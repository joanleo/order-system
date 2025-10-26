package com.joanleon.ordersystem.infrastructure.adapter.out.mapper;

import com.joanleon.ordersystem.domain.model.Pago;
import com.joanleon.ordersystem.infrastructure.adapter.out.entity.PagoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PagoMapper {
    
    private final FacturaMapper facturaMapper;
    
    public Pago toDomain(PagoEntity entity) {
        if (entity == null) return null;
        return new Pago(
            entity.getId(),
            facturaMapper.toDomain(entity.getFactura()),
            entity.getMonto(),
            entity.getMetodoPago(),
            entity.getReferencia(),
            entity.getFechaPago(),
            entity.getObservaciones()
        );
    }
    
    public PagoEntity toEntity(Pago domain) {
        if (domain == null) return null;
        return PagoEntity.builder()
            .id(domain.getId())
            .factura(facturaMapper.toEntity(domain.getFactura()))
            .monto(domain.getMonto())
            .metodoPago(domain.getMetodoPago())
            .referencia(domain.getReferencia())
            .fechaPago(domain.getFechaPago())
            .observaciones(domain.getObservaciones())
            .build();
    }
}