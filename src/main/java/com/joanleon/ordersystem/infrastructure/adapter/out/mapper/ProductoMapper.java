package com.joanleon.ordersystem.infrastructure.adapter.out.mapper;

import com.joanleon.ordersystem.domain.model.Producto;
import com.joanleon.ordersystem.infrastructure.adapter.out.entity.ProductoEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {
    
    public Producto toDomain(ProductoEntity entity) {
        if (entity == null) return null;
        return new Producto(
            entity.getId(),
            entity.getCodigo(),
            entity.getNombre(),
            entity.getDescripcion(),
            entity.getPrecio(),
            entity.getStock()
        );
    }
    
    public ProductoEntity toEntity(Producto domain) {
        if (domain == null) return null;
        return ProductoEntity.builder()
            .id(domain.getId())
            .codigo(domain.getCodigo())
            .nombre(domain.getNombre())
            .descripcion(domain.getDescripcion())
            .precio(domain.getPrecio())
            .stock(domain.getStock())
            .build();
    }
}
