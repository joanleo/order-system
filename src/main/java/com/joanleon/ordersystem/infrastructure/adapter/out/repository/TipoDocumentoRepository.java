package com.joanleon.ordersystem.infrastructure.adapter.out.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joanleon.ordersystem.infrastructure.adapter.out.entity.TipoDocumentoEntity;

public interface TipoDocumentoRepository extends JpaRepository<TipoDocumentoEntity, Long> {
    Optional<TipoDocumentoEntity> findByCodigo(String codigo);
}