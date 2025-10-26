package com.joanleon.ordersystem.infrastructure.adapter.out.repository;

import com.joanleon.ordersystem.infrastructure.adapter.out.entity.PagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagoRepository extends JpaRepository<PagoEntity, Long> {
    
    List<PagoEntity> findByFacturaId(Long facturaId);
    
    List<PagoEntity> findByFacturaClienteId(Long clienteId);
}