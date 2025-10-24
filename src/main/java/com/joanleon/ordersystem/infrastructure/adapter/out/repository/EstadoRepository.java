package com.joanleon.ordersystem.infrastructure.adapter.out.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joanleon.ordersystem.infrastructure.adapter.out.entity.EstadoEntity;

public interface EstadoRepository extends JpaRepository<EstadoEntity, Long> {
    List<EstadoEntity> findByTipoDocumentoId(Long tipoDocumentoId);
}
