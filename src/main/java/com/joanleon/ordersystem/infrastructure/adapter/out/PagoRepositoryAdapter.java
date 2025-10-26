package com.joanleon.ordersystem.infrastructure.adapter.out;

import com.joanleon.ordersystem.application.port.out.PagoRepositoryPort;
import com.joanleon.ordersystem.domain.model.Pago;
import com.joanleon.ordersystem.infrastructure.adapter.out.mapper.PagoMapper;
import com.joanleon.ordersystem.infrastructure.adapter.out.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PagoRepositoryAdapter implements PagoRepositoryPort {
    
    private final PagoRepository repository;
    private final PagoMapper mapper;
    
    @Override
    public Pago save(Pago pago) {
        var entity = mapper.toEntity(pago);
        var savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }
    
    @Override
    public Optional<Pago> findById(Long id) {
        return repository.findById(id)
            .map(mapper::toDomain);
    }
    
    @Override
    public List<Pago> findByFacturaId(Long facturaId) {
        return repository.findByFacturaId(facturaId)
            .stream()
            .map(mapper::toDomain)
            .toList();
    }
    
    @Override
    public List<Pago> findByFacturaClienteId(Long clienteId) {
        return repository.findByFacturaClienteId(clienteId)
            .stream()
            .map(mapper::toDomain)
            .toList();
    }
    
    @Override
    public List<Pago> findAll() {
        return repository.findAll()
            .stream()
            .map(mapper::toDomain)
            .toList();
    }
}