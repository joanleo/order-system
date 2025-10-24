package com.joanleon.ordersystem.infrastructure.adapter.out.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joanleon.ordersystem.infrastructure.adapter.out.entity.ProductoEntity;

public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {
    Optional<ProductoEntity> findByCodigo(Integer codigo);
}