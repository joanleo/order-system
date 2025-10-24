package com.joanleon.ordersystem.application.port.in;

import com.joanleon.ordersystem.application.dto.ProductoRequest;
import com.joanleon.ordersystem.application.dto.ProductoResponse;

import java.util.List;

public interface ProductoUseCase {
    ProductoResponse crearProducto(ProductoRequest request);
    ProductoResponse obtenerProductoPorId(Long id);
    ProductoResponse obtenerProductoPorCodigo(Integer codigo);
    List<ProductoResponse> listar();
    ProductoResponse actualizarProducto(Long id, ProductoRequest request);
    void eliminarProducto(Long id);
}
