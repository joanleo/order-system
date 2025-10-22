package com.joanleon.ordersystem.application.service;

import org.springframework.stereotype.Service;

import com.joanleon.ordersystem.application.dto.PedidoRequest;
import com.joanleon.ordersystem.application.dto.PedidoResponse;
import com.joanleon.ordersystem.application.port.in.PedidoUseCase;
import com.joanleon.ordersystem.application.port.out.ClienteRepositoryPort;
import com.joanleon.ordersystem.application.port.out.PedidoRepositoryPort;
import com.joanleon.ordersystem.domain.model.Cliente;
import com.joanleon.ordersystem.domain.model.Pedido;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService implements PedidoUseCase {

    private final PedidoRepositoryPort pedidoRepository;
    private final ClienteRepositoryPort clienteRepository;

    @Override
    public PedidoResponse crearPedido(PedidoRequest request) {
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Pedido pedido = new Pedido(cliente, request.getDetalles());
        Pedido pedidoGuardado = pedidoRepository.save(pedido);
        return PedidoResponse.fromDomain(pedidoGuardado);
    }
}
