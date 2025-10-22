package com.joanleon.ordersystem.application.dto;

import lombok.Data;
import java.util.List;

import com.joanleon.ordersystem.domain.model.DetallePedido;

@Data
public class PedidoRequest {
    private Long clienteId;
    private List<DetallePedido> detalles;
}
