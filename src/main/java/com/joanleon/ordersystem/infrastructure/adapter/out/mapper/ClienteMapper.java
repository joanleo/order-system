package com.joanleon.ordersystem.infrastructure.adapter.out.mapper;

import org.springframework.stereotype.Component;

import com.joanleon.ordersystem.domain.model.Cliente;
import com.joanleon.ordersystem.infrastructure.adapter.out.entity.ClienteEntity;

@Component
public class ClienteMapper {
	
	public Cliente toDomain(ClienteEntity entity) {
        return Cliente.builder()
            .id(entity.getId())
            .nombre(entity.getNombre())
            .email(entity.getEmail())
            .telefono(entity.getTelefono())
            .activo(entity.isActivo())
            .build();
    }
	
	public ClienteEntity toEntity(Cliente cliente) {
        // Mapear de dominio a entidad
        ClienteEntity entity = new ClienteEntity();
        entity.setId(cliente.getId());
        entity.setNombre(cliente.getNombre());
        entity.setEmail(cliente.getEmail());
        entity.setTelefono(cliente.getTelefono());
        entity.setActivo(cliente.isActivo());
        return entity;
    }

}
