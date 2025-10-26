package com.joanleon.ordersystem.infrastructure.adapter.out;

import com.joanleon.ordersystem.application.port.out.FacturaRepositoryPort;
import com.joanleon.ordersystem.domain.model.Factura;
import com.joanleon.ordersystem.infrastructure.adapter.out.mapper.FacturaMapper;
import com.joanleon.ordersystem.infrastructure.adapter.out.repository.FacturaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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
}