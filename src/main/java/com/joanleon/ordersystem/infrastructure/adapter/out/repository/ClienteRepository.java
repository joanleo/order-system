package com.joanleon.ordersystem.infrastructure.adapter.out.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joanleon.ordersystem.infrastructure.adapter.out.entity.ClienteEntity;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

	// Buscar todos los clientes activos
    List<ClienteEntity> findAllByActivoTrue();

    // Buscar un cliente activo por ID
    Optional<ClienteEntity> findByIdAndActivoTrue(Long id);

}
