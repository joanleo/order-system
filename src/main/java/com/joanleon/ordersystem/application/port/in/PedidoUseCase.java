package com.joanleon.ordersystem.application.port.in;

import java.util.List;

import com.joanleon.ordersystem.application.dto.PedidoRequest;
import com.joanleon.ordersystem.application.dto.PedidoResponse;

public interface PedidoUseCase {
	PedidoResponse crearPedido(PedidoRequest request);
    PedidoResponse obtenerPedidoPorId(Long id);
    List<PedidoResponse> listar();
    List<PedidoResponse> listarPorCliente(Long clienteId);
    PedidoResponse cambiarEstado(Long pedidoId, Long estadoId);
    PedidoResponse cancelarPedido(Long pedidoId);
    void eliminarPedido(Long id);
}
