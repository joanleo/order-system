package com.joanleon.ordersystem.application.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.joanleon.ordersystem.application.dto.ClienteRequest;
import com.joanleon.ordersystem.application.dto.ClienteResponse;
import com.joanleon.ordersystem.application.port.in.ClienteUseCase;
import com.joanleon.ordersystem.application.port.out.ClienteRepositoryPort;
import com.joanleon.ordersystem.domain.model.Cliente;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService implements ClienteUseCase {

	private final ClienteRepositoryPort clienteRepository;

    @Override
    public ClienteResponse crearCliente(ClienteRequest request) {
        Cliente cliente = Cliente.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .telefono(request.getTelefono())
                .build();

        Cliente clienteGuardado = clienteRepository.save(cliente);
        return ClienteResponse.fromDomain(clienteGuardado);
    }

    @Override
    public ClienteResponse obtenerClientePorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        
        return ClienteResponse.fromDomain(cliente);
    }

    @Override
    public List<ClienteResponse> listar() {
        return clienteRepository.findAll()
                .stream()
                .map(ClienteResponse::fromDomain)
                .toList();
    }

    @Override
    public ClienteResponse actualizarCliente(Long id, ClienteRequest request) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        
     // Actualiza solo los campos que no son nulos en el request
        if (request.getNombre() != null) {
            clienteExistente.setNombre(request.getNombre());
        }
        if (request.getEmail() != null) {
            clienteExistente.setEmail(request.getEmail());
        }
        if (request.getTelefono() != null) {
            clienteExistente.setTelefono(request.getTelefono());
        }
        // Para el campo booleano, puedes establecer un valor por defecto si no se envía
        clienteExistente.setActivo(request.isActivo());

        Cliente clienteActualizado = clienteRepository.save(clienteExistente);
        return ClienteResponse.fromDomain(clienteActualizado);
    }

    @Override
    public void eliminarCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        clienteRepository.delete(cliente);
    }

}