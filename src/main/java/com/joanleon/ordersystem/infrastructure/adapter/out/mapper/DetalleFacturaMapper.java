package com.joanleon.ordersystem.infrastructure.adapter.out.mapper;

import com.joanleon.ordersystem.domain.model.DetalleFactura;
import com.joanleon.ordersystem.infrastructure.adapter.out.entity.DetalleFacturaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DetalleFacturaMapper {
    
    private final ProductoMapper productoMapper;
    
    public DetalleFactura toDomain(DetalleFacturaEntity entity) {
        if (entity == null) return null;
        return new DetalleFactura(
            entity.getId(),
            productoMapper.toDomain(entity.getProducto()),
            entity.getCantidad(),
            entity.getPrecioUnitario(),
            entity.getDescuentoLinea(),
            entity.getSubtotal()
        );
    }
    
    public DetalleFacturaEntity toEntity(DetalleFactura domain) {
        if (domain == null) return null;
        return DetalleFacturaEntity.builder()
            .id(domain.getId())
            .producto(productoMapper.toEntity(domain.getProducto()))
            .cantidad(domain.getCantidad())
            .precioUnitario(domain.getPrecioUnitario())
            .descuentoLinea(domain.getDescuentoLinea())
            .subtotal(domain.getSubtotal())
            .build();
    }
}