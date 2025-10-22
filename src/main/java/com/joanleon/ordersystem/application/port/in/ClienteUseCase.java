package com.joanleon.ordersystem.application.port.in;

import java.util.List;

import com.joanleon.ordersystem.application.dto.ClienteRequest;
import com.joanleon.ordersystem.application.dto.ClienteResponse;

public interface ClienteUseCase {
    ClienteResponse crearCliente(ClienteRequest request);
    ClienteResponse obtenerClientePorId(Long id);
	List<ClienteResponse> listar();
	ClienteResponse actualizarCliente(Long id, ClienteRequest request);
    void eliminarCliente(Long id);
}
