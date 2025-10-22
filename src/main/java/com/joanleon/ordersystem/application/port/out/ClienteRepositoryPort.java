package com.joanleon.ordersystem.application.port.out;

import java.util.List;
import java.util.Optional;

import com.joanleon.ordersystem.domain.model.Cliente;

public interface ClienteRepositoryPort {
    Cliente save(Cliente cliente);
    Optional<Cliente> findById(Long id);
    List<Cliente> findAll();
	void delete(Cliente cliente);
	Optional<Cliente> findByIdActivo(Long id);
}