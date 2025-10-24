package com.joanleon.ordersystem.infrastructure.adapter.out;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.joanleon.ordersystem.application.port.out.ClienteRepositoryPort;
import com.joanleon.ordersystem.domain.model.Cliente;
import com.joanleon.ordersystem.infrastructure.adapter.out.entity.ClienteEntity;
import com.joanleon.ordersystem.infrastructure.adapter.out.mapper.ClienteMapper;
import com.joanleon.ordersystem.infrastructure.adapter.out.repository.ClienteRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ClienteRepositoryAdapter implements ClienteRepositoryPort {
    
    private final ClienteRepository jpaRepository;
    private final ClienteMapper mapper;

    @Override
    public Cliente save(Cliente cliente) {
    	ClienteEntity entity = mapper.toEntity(cliente);
        ClienteEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Cliente> findByIdActivo(Long id) {
        return jpaRepository.findByIdAndActivoTrue(id)
                .map(mapper::toDomain);
    }

    
    

	@Override
	public List<Cliente> findAll() {
		return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
	}

	@Override
	public void delete(Cliente cliente) {
        ClienteEntity entity = mapper.toEntity(cliente);
        jpaRepository.delete(entity);
    }

	@Override
	public Optional<Cliente> findById(Long id) {
		return jpaRepository.findById(id)
                .map(mapper::toDomain);
	}
}
