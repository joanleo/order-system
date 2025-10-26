package com.joanleon.ordersystem.infrastructure.adapter.out.mapper;

import com.joanleon.ordersystem.domain.model.Factura;
import com.joanleon.ordersystem.infrastructure.adapter.out.entity.FacturaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FacturaMapper {
    
    private final ClienteMapper clienteMapper;
    private final PedidoMapper pedidoMapper;
    private final DetalleFacturaMapper detalleFacturaMapper;
    private final EstadoMapper estadoMapper;
    private final TipoDocumentoMapper tipoDocumentoMapper;
    
    public Factura toDomain(FacturaEntity entity) {
        if (entity == null) return null;
        
        return Factura.reconstruirDesdeDB(
            entity.getId(),
            entity.getNumeroFactura(),
            clienteMapper.toDomain(entity.getCliente()),
            entity.getPedido() != null ? pedidoMapper.toDomain(entity.getPedido()) : null,
            entity.getDetalles().stream()
                .map(detalleFacturaMapper::toDomain)
                .toList(),
            estadoMapper.toDomain(entity.getEstado()),
            tipoDocumentoMapper.toDomain(entity.getTipoDocumento()),
            entity.getFechaEmision(),
            entity.getFechaVencimiento(),
            entity.getFechaCreacion(),
            entity.getSubtotal(),
            entity.getDescuento(),
            entity.getIva(),
            entity.getOtrosImpuestos(),
            entity.getTotal(),
            entity.getMetodoPago(),
            entity.getTerminosCondiciones(),
            entity.getObservaciones(),
            entity.getMontoPagado(),
            entity.getSaldoPendiente()
        );
    }
    
    public FacturaEntity toEntity(Factura domain) {
        if (domain == null) return null;
        
        var facturaEntity = FacturaEntity.builder()
            .id(domain.getId())
            .numeroFactura(domain.getNumeroFactura())
            .cliente(clienteMapper.toEntity(domain.getCliente()))
            .pedido(domain.getPedido() != null ? pedidoMapper.toEntity(domain.getPedido()) : null)
            .estado(estadoMapper.toEntity(domain.getEstado()))
            .tipoDocumento(tipoDocumentoMapper.toEntity(domain.getTipoDocumento()))
            .fechaEmision(domain.getFechaEmision())
            .fechaVencimiento(domain.getFechaVencimiento())
            .fechaCreacion(domain.getFechaCreacion())
            .subtotal(domain.getSubtotal())
            .descuento(domain.getDescuento())
            .iva(domain.getIva())
            .otrosImpuestos(domain.getOtrosImpuestos())
            .total(domain.getTotal())
            .metodoPago(domain.getMetodoPago())
            .terminosCondiciones(domain.getTerminosCondiciones())
            .observaciones(domain.getObservaciones())
            .montoPagado(domain.getMontoPagado())
            .saldoPendiente(domain.getSaldoPendiente())
            .build();
        
        // Mapear detalles y establecer la relación bidireccional
        var detalles = domain.getDetalles().stream()
            .map(detalle -> {
                var detalleEntity = detalleFacturaMapper.toEntity(detalle);
                detalleEntity.setFactura(facturaEntity);
                return detalleEntity;
            })
            .toList();
        
        facturaEntity.setDetalles(detalles);
        
        return facturaEntity;
    }
}