package com.joanleon.ordersystem.infrastructure.adapter.out.mapper;

import com.joanleon.ordersystem.domain.model.DetallePedido;
import com.joanleon.ordersystem.infrastructure.adapter.out.entity.DetallePedidoEntity;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DetallePedidoMapper {
    
    private final ProductoMapper productoMapper;
    
    public DetallePedido toDomain(DetallePedidoEntity entity) {
        if (entity == null) return null;
        return new DetallePedido(
            entity.getId(),
            productoMapper.toDomain(entity.getProducto()),
            entity.getCantidad(),
            entity.getPrecioUnitario()
        );
    }
    
    public DetallePedidoEntity toEntity(DetallePedido domain) {
        if (domain == null) return null;
        return DetallePedidoEntity.builder()
            .id(domain.getId())
            .producto(productoMapper.toEntity(domain.getProducto()))
            .cantidad(domain.getCantidad())
            .precioUnitario(domain.getPrecioUnitario())
            .build();
    }
}
