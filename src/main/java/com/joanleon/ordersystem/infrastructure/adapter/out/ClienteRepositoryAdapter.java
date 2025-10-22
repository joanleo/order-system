package com.joanleon.ordersystem.infrastructure.adapter.out;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.joanleon.ordersystem.application.port.out.ClienteRepositoryPort;
import com.joanleon.ordersystem.domain.model.Cliente;
import com.joanleon.ordersystem.infrastructure.adapter.out.entity.ClienteEntity;
import com.joanleon.ordersystem.infrastructure.adapter.out.repository.ClienteRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ClienteRepositoryAdapter implements ClienteRepositoryPort {
    
    private final ClienteRepository jpaRepository;

    @Override
    public Cliente save(Cliente cliente) {
        ClienteEntity entity = toEntity(cliente);
        ClienteEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Cliente> findByIdActivo(Long id) {
        return jpaRepository.findByIdAndActivoTrue(id)
                .map(this::toDomain);
    }

    private ClienteEntity toEntity(Cliente cliente) {
        // Mapear de dominio a entidad
        ClienteEntity entity = new ClienteEntity();
        entity.setId(cliente.getId());
        entity.setNombre(cliente.getNombre());
        entity.setEmail(cliente.getEmail());
        entity.setTelefono(cliente.getTelefono());
        entity.setActivo(cliente.isActivo());
        return entity;
    }
    private Cliente toDomain(ClienteEntity entity) {
        return Cliente.builder()
            .id(entity.getId())
            .nombre(entity.getNombre())
            .email(entity.getEmail())
            .telefono(entity.getTelefono())
            .activo(entity.isActivo())
            .build();
    }

	@Override
	public List<Cliente> findAll() {
		return jpaRepository.findAll()
                .stream()
                .map(this::toDomain)
                .toList();
	}

	@Override
	public void delete(Cliente cliente) {
        ClienteEntity entity = toEntity(cliente);
        jpaRepository.delete(entity);
    }

	@Override
	public Optional<Cliente> findById(Long id) {
		return jpaRepository.findById(id)
                .map(this::toDomain);
	}
}
