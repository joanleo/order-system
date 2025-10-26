package com.joanleon.ordersystem.application.service;

import com.joanleon.ordersystem.application.dto.ProductoRequest;
import com.joanleon.ordersystem.application.dto.ProductoResponse;
import com.joanleon.ordersystem.application.port.in.ProductoUseCase;
import com.joanleon.ordersystem.application.port.out.ProductoRepositoryPort;
import com.joanleon.ordersystem.domain.exception.CodigoProductoDuplicadoException;
import com.joanleon.ordersystem.domain.exception.ProductoNoEncontradoException;
import com.joanleon.ordersystem.domain.model.Producto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoService implements ProductoUseCase {

    private final ProductoRepositoryPort productoRepository;

    @Override
    public ProductoResponse crearProducto(ProductoRequest request) {
        // Validar que no exista el código
        productoRepository.findByCodigo(request.getCodigo())
            .ifPresent(p -> {
                throw new CodigoProductoDuplicadoException(request.getCodigo());
            });

        // Crear producto
        Producto producto = new Producto(
            null,
            request.getCodigo(),
            request.getNombre(),
            request.getDescripcion(),
            request.getPrecio(),
            request.getStock()
        );

        Producto productoGuardado = productoRepository.save(producto);
        return ProductoResponse.fromDomain(productoGuardado);
    }

    @Override
    public ProductoResponse obtenerProductoPorId(Long id) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new ProductoNoEncontradoException(id));
        return ProductoResponse.fromDomain(producto);
    }

    @Override
    public ProductoResponse obtenerProductoPorCodigo(Integer codigo) {
        Producto producto = productoRepository.findByCodigo(codigo)
            .orElseThrow(() -> new ProductoNoEncontradoException(codigo));
        return ProductoResponse.fromDomain(producto);
    }

    @Override
    public List<ProductoResponse> listar() {
        return productoRepository.findAll()
            .stream()
            .map(ProductoResponse::fromDomain)
            .collect(Collectors.toList());
    }

    @Override
    public ProductoResponse actualizarProducto(Long id, ProductoRequest request) {
        // Buscar producto existente
        Producto productoExistente = productoRepository.findById(id)
            .orElseThrow(() -> new ProductoNoEncontradoException(id));

        // Validar que el código no esté en uso por otro producto
        productoRepository.findByCodigo(request.getCodigo())
            .ifPresent(p -> {
                if (!p.getId().equals(id)) {
                    throw new CodigoProductoDuplicadoException(request.getCodigo());
                }
            });

        // Actualizar producto
        Producto productoActualizado = new Producto(
            id,
            request.getCodigo(),
            request.getNombre(),
            request.getDescripcion(),
            request.getPrecio(),
            request.getStock()
        );

        Producto productoGuardado = productoRepository.save(productoActualizado);
        return ProductoResponse.fromDomain(productoGuardado);
    }

    @Override
    public void eliminarProducto(Long id) {
        // Verificar que exista el producto
        productoRepository.findById(id)
            .orElseThrow(() -> new ProductoNoEncontradoException(id));
        
        productoRepository.deleteById(id);
    }
}