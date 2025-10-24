package com.joanleon.ordersystem.application.port.out;

import com.joanleon.ordersystem.domain.model.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoRepositoryPort {
    Optional<Producto> findById(Long id);
    Optional<Producto> findByCodigo(Integer codigo);
    List<Producto> findAll();
    Producto save(Producto producto);
    void deleteById(Long id);
}
