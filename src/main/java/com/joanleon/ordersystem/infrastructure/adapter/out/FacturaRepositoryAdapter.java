package com.joanleon.ordersystem.infrastructure.adapter.out;

import com.joanleon.ordersystem.application.dto.ClienteTopDTO;
import com.joanleon.ordersystem.application.dto.VentasMesDTO;
import com.joanleon.ordersystem.application.port.out.FacturaRepositoryPort;
import com.joanleon.ordersystem.domain.model.Factura;
import com.joanleon.ordersystem.infrastructure.adapter.out.mapper.FacturaMapper;
import com.joanleon.ordersystem.infrastructure.adapter.out.repository.FacturaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FacturaRepositoryAdapter implements FacturaRepositoryPort {
    
    private final FacturaRepository repository;
    private final FacturaMapper mapper;
    
    @Override
    public Factura save(Factura factura) {
        var entity = mapper.toEntity(factura);
        var savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }
    
    @Override
    public Optional<Factura> findById(Long id) {
        return repository.findById(id)
            .map(mapper::toDomain);
    }
    
    @Override
    public Optional<Factura> findByNumeroFactura(String numeroFactura) {
        return repository.findByNumeroFactura(numeroFactura)
            .map(mapper::toDomain);
    }
    
    @Override
    public List<Factura> findAll() {
        return repository.findAll()
            .stream()
            .map(mapper::toDomain)
            .toList();
    }
    
    @Override
    public List<Factura> findByClienteId(Long clienteId) {
        return repository.findByClienteId(clienteId)
            .stream()
            .map(mapper::toDomain)
            .toList();
    }
    
    @Override
    public List<Factura> findByEstadoId(Long estadoId) {
        return repository.findByEstadoId(estadoId)
            .stream()
            .map(mapper::toDomain)
            .toList();
    }
    
    @Override
    public List<Factura> findByFechaEmisionBetween(LocalDate fechaInicio, LocalDate fechaFin) {
        return repository.findByFechaEmisionBetween(fechaInicio, fechaFin)
            .stream()
            .map(mapper::toDomain)
            .toList();
    }
    
    @Override
    public List<Factura> findByClienteIdAndEstadoId(Long clienteId, Long estadoId) {
        return repository.findByClienteIdAndEstadoId(clienteId, estadoId)
            .stream()
            .map(mapper::toDomain)
            .toList();
    }
    
    @Override
    public List<Factura> findBySaldoPendienteGreaterThan(BigDecimal monto) {
        return repository.findBySaldoPendienteGreaterThan(monto)
            .stream()
            .map(mapper::toDomain)
            .toList();
    }
    
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
    
    @Override
    public String generarNumeroFactura() {
        int year = LocalDate.now().getYear();
        String prefijo = "FACT-" + year + "-";
        
        Integer maxConsecutivo = repository.findMaxConsecutivo(prefijo);
        int nuevoConsecutivo = (maxConsecutivo != null ? maxConsecutivo : 0) + 1;
        
        return prefijo + String.format("%04d", nuevoConsecutivo);
    }
    
    @Override
    public BigDecimal obtenerTotalFacturadoPorAnio(int anio) {
        return repository.obtenerTotalFacturadoPorAnio(anio);
    }
    
    @Override
    public BigDecimal obtenerTotalPagadoPorAnio(int anio) {
        return repository.obtenerTotalPagadoPorAnio(anio);
    }
    
    @Override
    public BigDecimal obtenerTotalPendientePorAnio(int anio) {
        return repository.obtenerTotalPendientePorAnio(anio);
    }
    
    @Override
    public Long contarFacturasPorAnio(int anio) {
        return repository.contarFacturasPorAnio(anio);
    }
    
    @Override
    public Long contarFacturasVencidas(int anio) {
        return repository.contarFacturasVencidas(anio);
    }
    
    @Override
    public Long contarFacturasPagadas(int anio) {
        return repository.contarFacturasPagadas(anio);
    }
    
    @Override
    public Long contarFacturasPendientes(int anio) {
        return repository.contarFacturasPendientes(anio);
    }
    
    @Override
    public List<VentasMesDTO> obtenerVentasPorMes(int anio) {
        return repository.obtenerVentasPorMes(anio);
    }
    
    @Override
    public List<ClienteTopDTO> obtenerTopClientes(int anio, int limite) {
        return repository.obtenerTopClientes(anio)
                .stream()
                .limit(limite)
                .toList();
    }
    
    @Override
    public Map<String, Long> obtenerDistribucionPorEstado(int anio) {
        List<Object[]> resultados = repository.obtenerDistribucionPorEstado(anio);
        Map<String, Long> distribucion = new HashMap<>();
        
        for (Object[] resultado : resultados) {
            String estado = (String) resultado[0];
            Long cantidad = (Long) resultado[1];
            distribucion.put(estado, cantidad);
        }
        
        return distribucion;
    }
}