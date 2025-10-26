package com.joanleon.ordersystem.infrastructure.adapter.out.repository;

import com.joanleon.ordersystem.infrastructure.adapter.out.entity.FacturaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FacturaRepository extends JpaRepository<FacturaEntity, Long> {
    
    Optional<FacturaEntity> findByNumeroFactura(String numeroFactura);
    
    List<FacturaEntity> findByClienteId(Long clienteId);
    
    List<FacturaEntity> findByEstadoId(Long estadoId);
    
    List<FacturaEntity> findByFechaEmisionBetween(LocalDate fechaInicio, LocalDate fechaFin);
    
    List<FacturaEntity> findByClienteIdAndEstadoId(Long clienteId, Long estadoId);
    
    @Query("SELECT MAX(CAST(SUBSTRING(f.numeroFactura, 11) AS int)) FROM FacturaEntity f WHERE f.numeroFactura LIKE :prefijo%")
    Integer findMaxConsecutivo(String prefijo);
    
    List<FacturaEntity> findBySaldoPendienteGreaterThan(java.math.BigDecimal monto);
}