package com.joanleon.ordersystem.domain.model;

import java.math.BigDecimal;
import java.util.List;

import com.joanleon.ordersystem.application.enums.EstadoPedido;

import lombok.Getter;

@Getter
public class Pedido {
    private Long id;
    private Cliente cliente;
    private List<DetallePedido> detalles;
    private EstadoPedido estado;

    public Pedido(Cliente cliente, List<DetallePedido> detalles) {
        this.cliente = cliente;
        this.detalles = detalles;
        this.estado = EstadoPedido.PENDIENTE;
    }

    public BigDecimal calcularTotal() {
        return detalles.stream()
                .map(DetallePedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
