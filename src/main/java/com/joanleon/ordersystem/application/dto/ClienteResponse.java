package com.joanleon.ordersystem.application.dto;

import com.joanleon.ordersystem.domain.model.Cliente;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClienteResponse {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private boolean activo;

    public static ClienteResponse fromDomain(Cliente cliente) {
        return ClienteResponse.builder()
                .id(cliente.getId())
                .nombre(cliente.getNombre())
                .email(cliente.getEmail())
                .telefono(cliente.getTelefono())
                .activo(cliente.isActivo())
                .build();
    }
}
