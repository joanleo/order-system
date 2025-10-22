package com.joanleon.ordersystem.infrastructure.adapter.in.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.joanleon.ordersystem.application.dto.PedidoRequest;
import com.joanleon.ordersystem.application.dto.PedidoResponse;
import com.joanleon.ordersystem.application.port.in.PedidoUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoUseCase crearPedidoUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PedidoResponse> crearPedido(@RequestBody PedidoRequest request) {
        PedidoResponse response = crearPedidoUseCase.crearPedido(request);
        return ResponseEntity.ok(response);
    }
}
