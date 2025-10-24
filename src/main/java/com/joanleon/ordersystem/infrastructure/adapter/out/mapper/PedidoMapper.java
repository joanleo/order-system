package com.joanleon.ordersystem.infrastructure.adapter.out.mapper;

import java.lang.reflect.Field;

import org.springframework.stereotype.Component;

import com.joanleon.ordersystem.domain.model.Pedido;
import com.joanleon.ordersystem.infrastructure.adapter.out.entity.PedidoEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PedidoMapper { 
    
    private final ClienteMapper clienteMapper;
    private final DetallePedidoMapper detallePedidoMapper;
    private final EstadoMapper estadoMapper;
    private final TipoDocumentoMapper tipoDocumentoMapper;
    
    public Pedido toDomain(PedidoEntity entity) {
        if (entity == null) return null;
        
        var pedido = new Pedido(
            clienteMapper.toDomain(entity.getCliente()),
            entity.getDetalles().stream()
                .map(detallePedidoMapper::toDomain)
                .toList(),
            estadoMapper.toDomain(entity.getEstado()),
            tipoDocumentoMapper.toDomain(entity.getTipoDocumento())
        );
        
        // Setear el ID usando reflection o un setter en Pedido
        // Si Pedido no tiene setter para id, podrías agregarlo o usar reflection
        // Para este ejemplo, asumimos que agregas un método setId en Pedido
        setId(pedido, entity.getId());
        
        return pedido;
    }
    
    public PedidoEntity toEntity(Pedido domain) {
    	if (domain == null) return null;
        
        var pedidoEntity = PedidoEntity.builder()
            .id(domain.getId())
            .cliente(clienteMapper.toEntity(domain.getCliente()))
            .estado(estadoMapper.toEntity(domain.getEstado()))
            .tipoDocumento(tipoDocumentoMapper.toEntity(domain.getTipoDocumento()))
            .build();
        
        // Mapear detalles y establecer la relación bidireccional
        var detalles = domain.getDetalles().stream()
            .map(detalle -> {
                var detalleEntity = detallePedidoMapper.toEntity(detalle);
                detalleEntity.setPedido(pedidoEntity);
                return detalleEntity;
            })
            .toList();
        
        pedidoEntity.setDetalles(detalles);
        
        return pedidoEntity;
    }
    
    private void setId(Pedido pedido, Long id) {
        try {
            Field field = Pedido.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(pedido, id);
        } catch (Exception e) {
            throw new RuntimeException("Error al setear el ID del pedido", e);
        }
    }
}
