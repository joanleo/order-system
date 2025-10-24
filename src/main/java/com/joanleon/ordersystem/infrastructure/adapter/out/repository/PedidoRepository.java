package com.joanleon.ordersystem.infrastructure.adapter.out.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joanleon.ordersystem.infrastructure.adapter.out.entity.PedidoEntity;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {
	List<PedidoEntity> findByClienteId(Long clienteId);
}
