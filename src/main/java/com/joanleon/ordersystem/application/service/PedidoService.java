package com.joanleon.ordersystem.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.joanleon.ordersystem.application.dto.PedidoRequest;
import com.joanleon.ordersystem.application.dto.PedidoResponse;
import com.joanleon.ordersystem.application.port.in.PedidoUseCase;
import com.joanleon.ordersystem.application.port.out.ClienteRepositoryPort;
import com.joanleon.ordersystem.application.port.out.EstadoRepositoryPort;
import com.joanleon.ordersystem.application.port.out.PedidoRepositoryPort;
import com.joanleon.ordersystem.application.port.out.ProductoRepositoryPort;
import com.joanleon.ordersystem.application.port.out.TipoDocumentoRepositoryPort;
import com.joanleon.ordersystem.domain.model.Cliente;
import com.joanleon.ordersystem.domain.model.DetallePedido;
import com.joanleon.ordersystem.domain.model.Estado;
import com.joanleon.ordersystem.domain.model.Pedido;
import com.joanleon.ordersystem.domain.model.Producto;
import com.joanleon.ordersystem.domain.model.TipoDocumento;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService implements PedidoUseCase {

    private final PedidoRepositoryPort pedidoRepository;
    private final ClienteRepositoryPort clienteRepository;
    private final EstadoRepositoryPort estadoRepository;
    private final TipoDocumentoRepositoryPort tipoDocumentoRepository;
    private final ProductoRepositoryPort productoRepository;

    @Override
    public PedidoResponse crearPedido(PedidoRequest request) {
        // Buscar cliente
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        // Buscar tipo de documento PEDIDO
        TipoDocumento tipoDocumento = tipoDocumentoRepository.findByCodigo("PEDIDO")
                .orElseThrow(() -> new RuntimeException("Tipo de documento PEDIDO no encontrado"));

        // Buscar el primer estado para tipo PEDIDO (debería ser "Pendiente")
        Estado estadoInicial = estadoRepository.findByTipoDocumentoId(tipoDocumento.getId())
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No hay estados configurados para PEDIDO"));

        // Crear detalles del pedido
        List<DetallePedido> detalles = request.getDetalles().stream()
                .map(detalleRequest -> {
                    Producto producto = productoRepository.findById(detalleRequest.getProductoId())
                            .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + detalleRequest.getProductoId()));
                    
                    // Validar stock
                    if (producto.getStock() < detalleRequest.getCantidad()) {
                        throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
                    }
                    
                    return new DetallePedido(producto, detalleRequest.getCantidad());
                })
                .toList();

        // Crear pedido con estado inicial y tipo documento
        Pedido pedido = new Pedido(cliente, detalles, estadoInicial, tipoDocumento);
        
        // Guardar y retornar
        Pedido pedidoGuardado = pedidoRepository.save(pedido);
        return PedidoResponse.fromDomain(pedidoGuardado);
    }

    @Override
    public PedidoResponse obtenerPedidoPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));
        return PedidoResponse.fromDomain(pedido);
    }

    @Override
    public List<PedidoResponse> listar() {
        return pedidoRepository.findAll()
                .stream()
                .map(PedidoResponse::fromDomain)
                .toList();
    }

    @Override
    public List<PedidoResponse> listarPorCliente(Long clienteId) {
        // Validar que existe el cliente
        clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        
        return pedidoRepository.findByClienteId(clienteId)
                .stream()
                .map(PedidoResponse::fromDomain)
                .toList();
    }

    @Override
    public PedidoResponse cambiarEstado(Long pedidoId, Long estadoId) {
        // Buscar pedido
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        // Buscar nuevo estado
        Estado nuevoEstado = estadoRepository.findById(estadoId)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        // Cambiar estado (el método cambiarEstado del dominio valida que pertenezca al tipo de documento)
        pedido.cambiarEstado(nuevoEstado);

        // Guardar y retornar
        Pedido pedidoActualizado = pedidoRepository.save(pedido);
        return PedidoResponse.fromDomain(pedidoActualizado);
    }

    @Override
    public PedidoResponse cancelarPedido(Long pedidoId) {
        // Buscar pedido
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        // Buscar tipo de documento
        TipoDocumento tipoDocumento = tipoDocumentoRepository.findByCodigo("PEDIDO")
                .orElseThrow(() -> new RuntimeException("Tipo de documento PEDIDO no encontrado"));

        // Buscar estado "Cancelado"
        Estado estadoCancelado = estadoRepository.findByTipoDocumentoId(tipoDocumento.getId())
                .stream()
                .filter(e -> e.getDescripcion().equalsIgnoreCase("Cancelado"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Estado Cancelado no encontrado"));

        // Validar que el pedido no esté entregado
        if (pedido.getEstado().getDescripcion().equalsIgnoreCase("Entregado")) {
            throw new RuntimeException("No se puede cancelar un pedido que ya fue entregado");
        }

        // Cambiar estado
        pedido.cambiarEstado(estadoCancelado);

        // Guardar y retornar
        Pedido pedidoActualizado = pedidoRepository.save(pedido);
        return PedidoResponse.fromDomain(pedidoActualizado);
    }

    @Override
    public void eliminarPedido(Long id) {
        // Buscar pedido
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        // Validar que solo se puedan eliminar pedidos Pendientes o Cancelados
        String estadoActual = pedido.getEstado().getDescripcion();
        if (!estadoActual.equalsIgnoreCase("Pendiente") && !estadoActual.equalsIgnoreCase("Cancelado")) {
            throw new RuntimeException("Solo se pueden eliminar pedidos en estado Pendiente o Cancelado");
        }

        pedidoRepository.deleteById(id);
    }
}