package com.joanleon.ordersystem.domain.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;

@Getter
public class Pedido {
    private Long id;
    private Cliente cliente;
    private List<DetallePedido> detalles;
    private Estado estado;
    private TipoDocumento tipoDocumento;

    public Pedido(Cliente cliente, List<DetallePedido> detalles, Estado estadoInicial, TipoDocumento tipoDocumento) {
        this.cliente = cliente;
        this.detalles = detalles;
        this.estado = estadoInicial;
        this.tipoDocumento = tipoDocumento;
    }
    
    void setId(Long id) {
        this.id = id;
    }

    public BigDecimal calcularTotal() {
        return detalles.stream()
                .map(DetallePedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public void cambiarEstado(Estado nuevoEstado) {
        if (!nuevoEstado.getTipoDocumento().getId().equals(this.tipoDocumento.getId())) {
            throw new IllegalArgumentException("El estado no pertenece al tipo de documento del pedido");
        }
        this.estado = nuevoEstado;
    }
}
