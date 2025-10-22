package com.joanleon.ordersystem.application.port.in;

import com.joanleon.ordersystem.application.dto.PedidoRequest;
import com.joanleon.ordersystem.application.dto.PedidoResponse;

public interface PedidoUseCase {
    PedidoResponse crearPedido(PedidoRequest request);
}
