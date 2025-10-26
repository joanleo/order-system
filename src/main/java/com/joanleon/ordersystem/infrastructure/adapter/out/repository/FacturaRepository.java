package com.joanleon.ordersystem.infrastructure.adapter.out.repository;

import com.joanleon.ordersystem.application.dto.ClienteTopDTO;
import com.joanleon.ordersystem.application.dto.VentasMesDTO;
import com.joanleon.ordersystem.infrastructure.adapter.out.entity.FacturaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
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
    
// ==================== QUERIES PARA ESTADÍSTICAS ====================
    
    /**
     * Obtener total facturado en un año
     */
    @Query("SELECT COALESCE(SUM(f.total), 0) FROM FacturaEntity f " +
           "WHERE YEAR(f.fechaEmision) = :anio")
    BigDecimal obtenerTotalFacturadoPorAnio(@Param("anio") int anio);
    
    /**
     * Obtener total pagado en un año
     */
    @Query("SELECT COALESCE(SUM(f.montoPagado), 0) FROM FacturaEntity f " +
           "WHERE YEAR(f.fechaEmision) = :anio")
    BigDecimal obtenerTotalPagadoPorAnio(@Param("anio") int anio);
    
    /**
     * Obtener total pendiente en un año
     */
    @Query("SELECT COALESCE(SUM(f.saldoPendiente), 0) FROM FacturaEntity f " +
           "WHERE YEAR(f.fechaEmision) = :anio")
    BigDecimal obtenerTotalPendientePorAnio(@Param("anio") int anio);
    
    /**
     * Contar facturas por año
     */
    @Query("SELECT COUNT(f) FROM FacturaEntity f " +
           "WHERE YEAR(f.fechaEmision) = :anio")
    Long contarFacturasPorAnio(@Param("anio") int anio);
    
    /**
     * Contar facturas vencidas
     */
    @Query("SELECT COUNT(f) FROM FacturaEntity f " +
           "WHERE f.fechaVencimiento < CURRENT_DATE " +
           "AND f.saldoPendiente > 0 " +
           "AND YEAR(f.fechaEmision) = :anio")
    Long contarFacturasVencidas(@Param("anio") int anio);
    
    /**
     * Contar facturas pagadas
     */
    @Query("SELECT COUNT(f) FROM FacturaEntity f " +
           "WHERE f.saldoPendiente = 0 " +
           "AND YEAR(f.fechaEmision) = :anio")
    Long contarFacturasPagadas(@Param("anio") int anio);
    
    /**
     * Contar facturas pendientes
     */
    @Query("SELECT COUNT(f) FROM FacturaEntity f " +
           "WHERE f.saldoPendiente > 0 " +
           "AND f.fechaVencimiento >= CURRENT_DATE " +
           "AND YEAR(f.fechaEmision) = :anio")
    Long contarFacturasPendientes(@Param("anio") int anio);
    
    /**
     * Obtener ventas por mes
     */
    @Query("SELECT new com.joanleon.ordersystem.application.dto.VentasMesDTO(" +
           "MONTH(f.fechaEmision), YEAR(f.fechaEmision), COALESCE(SUM(f.total), 0)) " +
           "FROM FacturaEntity f " +
           "WHERE YEAR(f.fechaEmision) = :anio " +
           "GROUP BY MONTH(f.fechaEmision), YEAR(f.fechaEmision) " +
           "ORDER BY MONTH(f.fechaEmision)")
    List<VentasMesDTO> obtenerVentasPorMes(@Param("anio") int anio);
    
    /**
     * Obtener top 5 clientes
     */
    @Query("SELECT new com.joanleon.ordersystem.application.dto.ClienteTopDTO(" +
           "c.id, c.nombre, COALESCE(SUM(f.total), 0), COUNT(f)) " +
           "FROM FacturaEntity f JOIN f.cliente c " +
           "WHERE YEAR(f.fechaEmision) = :anio " +
           "GROUP BY c.id, c.nombre " +
           "ORDER BY SUM(f.total) DESC")
    List<ClienteTopDTO> obtenerTopClientes(@Param("anio") int anio);
    
    /**
     * Obtener distribución de facturas por estado
     */
    @Query("SELECT e.descripcion, COUNT(f) " +
           "FROM FacturaEntity f JOIN f.estado e " +
           "WHERE YEAR(f.fechaEmision) = :anio " +
           "GROUP BY e.descripcion")
    List<Object[]> obtenerDistribucionPorEstado(@Param("anio") int anio);
}