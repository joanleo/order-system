package com.joanleon.ordersystem.application.service;

import com.joanleon.ordersystem.application.dto.*;
import com.joanleon.ordersystem.application.port.in.FacturaUseCase;
import com.joanleon.ordersystem.application.port.out.*;
import com.joanleon.ordersystem.domain.exception.ClienteNoEncontradoException;
import com.joanleon.ordersystem.domain.exception.EstadoNoEncontradoException;
import com.joanleon.ordersystem.domain.exception.FacturaAnuladaException;
import com.joanleon.ordersystem.domain.exception.FacturaConPagosException;
import com.joanleon.ordersystem.domain.exception.FacturaNoEncontradaException;
import com.joanleon.ordersystem.domain.exception.FacturaYaPagadaException;
import com.joanleon.ordersystem.domain.exception.PagoInvalidoException;
import com.joanleon.ordersystem.domain.exception.PedidoNoEncontradoException;
import com.joanleon.ordersystem.domain.exception.PedidoNoFacturableException;
import com.joanleon.ordersystem.domain.exception.ProductoNoEncontradoException;
import com.joanleon.ordersystem.domain.exception.TipoDocumentoNoEncontradoException;
import com.joanleon.ordersystem.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FacturaService implements FacturaUseCase {

    private final FacturaRepositoryPort facturaRepository;
    private final PagoRepositoryPort pagoRepository;
    private final ClienteRepositoryPort clienteRepository;
    private final PedidoRepositoryPort pedidoRepository;
    private final ProductoRepositoryPort productoRepository;
    private final EstadoRepositoryPort estadoRepository;
    private final TipoDocumentoRepositoryPort tipoDocumentoRepository;

    @Override
    @Transactional
    public FacturaResponse crearFacturaDesdePedido(FacturaRequest request) {
        // Validar que venga el pedidoId
        if (request.getPedidoId() == null) {
            throw new PagoInvalidoException("El ID del pedido es obligatorio para este tipo de factura");
        }

        // Buscar pedido
        Pedido pedido = pedidoRepository.findById(request.getPedidoId())
            .orElseThrow(() -> new PedidoNoEncontradoException(request.getPedidoId()));

        // Validar que el pedido esté en estado "Entregado"
        if (!pedido.getEstado().getDescripcion().equalsIgnoreCase("Entregado")) {
            throw new PedidoNoFacturableException("Solo se pueden facturar pedidos en estado 'Entregado'");
        }

        // Buscar tipo de documento FACTURA
        TipoDocumento tipoDocumento = tipoDocumentoRepository.findByCodigo("FACTURA")
            .orElseThrow(() -> new TipoDocumentoNoEncontradoException("FACTURA"));

        // Buscar el estado inicial para facturas (debería ser "Emitida")
        Estado estadoInicial = estadoRepository.findByTipoDocumentoId(tipoDocumento.getId())
            .stream()
            .filter(e -> e.getDescripcion().equalsIgnoreCase("Emitida"))
            .findFirst()
            .orElseThrow(() -> new EstadoNoEncontradoException("Emitida", "FACTURA"));

        // Generar número de factura
        String numeroFactura = facturaRepository.generarNumeroFactura();

        // Crear factura desde pedido
        Factura factura = new Factura(
            pedido,
            numeroFactura,
            request.getFechaEmision(),
            request.getFechaVencimiento(),
            estadoInicial,
            tipoDocumento,
            request.getDescuento() != null ? request.getDescuento() : BigDecimal.ZERO,
            request.getTasaIva() != null ? request.getTasaIva() : new BigDecimal("0.19"),
            request.getMetodoPago()
        );

        // Guardar y retornar
        Factura facturaGuardada = facturaRepository.save(factura);
        return FacturaResponse.fromDomain(facturaGuardada);
    }

    @Override
    @Transactional
    public FacturaResponse crearFacturaManual(FacturaRequest request) {
        // Validar que vengan los detalles
        if (request.getDetalles() == null || request.getDetalles().isEmpty()) {
            throw new PagoInvalidoException("La factura debe tener al menos un detalle");
        }

        // Buscar cliente
        Cliente cliente = clienteRepository.findById(request.getClienteId())
            .orElseThrow(() -> new ClienteNoEncontradoException(request.getClienteId()));

        // Buscar tipo de documento FACTURA
        TipoDocumento tipoDocumento = tipoDocumentoRepository.findByCodigo("FACTURA")
            .orElseThrow(() -> new TipoDocumentoNoEncontradoException("Tipo de documento FACTURA no encontrado"));

        // Buscar el estado inicial
        Estado estadoInicial = estadoRepository.findByTipoDocumentoId(tipoDocumento.getId())
            .stream()
            .filter(e -> e.getDescripcion().equalsIgnoreCase("Emitida"))
            .findFirst()
            .orElseThrow(() -> new EstadoNoEncontradoException("Emitida", "FACTURA"));

        // Generar número de factura
        String numeroFactura = facturaRepository.generarNumeroFactura();

        // Crear detalles
        List<DetalleFactura> detalles = request.getDetalles().stream()
            .map(detalleRequest -> {
                Producto producto = productoRepository.findById(detalleRequest.getProductoId())
                    .orElseThrow(() -> new ProductoNoEncontradoException(detalleRequest.getProductoId()));

                return new DetalleFactura(
                    producto,
                    detalleRequest.getCantidad(),
                    detalleRequest.getDescuentoLinea() != null ? detalleRequest.getDescuentoLinea() : BigDecimal.ZERO
                );
            })
            .toList();

        // Crear factura manual
        Factura factura = new Factura(
            cliente,
            detalles,
            numeroFactura,
            request.getFechaEmision(),
            request.getFechaVencimiento(),
            estadoInicial,
            tipoDocumento,
            request.getDescuento() != null ? request.getDescuento() : BigDecimal.ZERO,
            request.getTasaIva() != null ? request.getTasaIva() : new BigDecimal("0.19"),
            request.getMetodoPago()
        );

        // Guardar y retornar
        Factura facturaGuardada = facturaRepository.save(factura);
        return FacturaResponse.fromDomain(facturaGuardada);
    }

    @Override
    public FacturaResponse obtenerFacturaPorId(Long id) {
        Factura factura = facturaRepository.findById(id)
            .orElseThrow(() -> new FacturaNoEncontradaException(id));
        return FacturaResponse.fromDomain(factura);
    }

    @Override
    public FacturaResponse obtenerFacturaPorNumero(String numeroFactura) {
        Factura factura = facturaRepository.findByNumeroFactura(numeroFactura)
            .orElseThrow(() -> new FacturaNoEncontradaException(numeroFactura));
        return FacturaResponse.fromDomain(factura);
    }

    @Override
    public List<FacturaResponse> listarTodas() {
        return facturaRepository.findAll()
            .stream()
            .map(FacturaResponse::fromDomain)
            .toList();
    }

    @Override
    public List<FacturaResponse> listarPorCliente(Long clienteId) {
        // Validar que existe el cliente
        clienteRepository.findById(clienteId)
            .orElseThrow(() -> new ClienteNoEncontradoException(clienteId));

        return facturaRepository.findByClienteId(clienteId)
            .stream()
            .map(FacturaResponse::fromDomain)
            .toList();
    }

    @Override
    public List<FacturaResponse> listarPorEstado(Long estadoId) {
        // Validar que existe el estado
        estadoRepository.findById(estadoId)
            .orElseThrow(() -> new EstadoNoEncontradoException(estadoId));

        return facturaRepository.findByEstadoId(estadoId)
            .stream()
            .map(FacturaResponse::fromDomain)
            .toList();
    }

    @Override
    public List<FacturaResponse> listarPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio.isAfter(fechaFin)) {
            throw new RuntimeException("La fecha de inicio no puede ser posterior a la fecha de fin");
        }

        return facturaRepository.findByFechaEmisionBetween(fechaInicio, fechaFin)
            .stream()
            .map(FacturaResponse::fromDomain)
            .toList();
    }

    @Override
    public List<FacturaResponse> listarFacturasPendientesPorCliente(Long clienteId) {
        // Buscar tipo de documento
        TipoDocumento tipoDocumento = tipoDocumentoRepository.findByCodigo("FACTURA")
            .orElseThrow(() -> new TipoDocumentoNoEncontradoException("FACTURA"));

        // Buscar estado "Emitida" o "Parcialmente Pagada"
        List<Estado> estadosPendientes = estadoRepository.findByTipoDocumentoId(tipoDocumento.getId())
            .stream()
            .filter(e -> e.getDescripcion().equalsIgnoreCase("Emitida") 
                      || e.getDescripcion().equalsIgnoreCase("Parcialmente Pagada"))
            .toList();

        // Obtener facturas
        return estadosPendientes.stream()
            .flatMap(estado -> facturaRepository.findByClienteIdAndEstadoId(clienteId, estado.getId()).stream())
            .map(FacturaResponse::fromDomain)
            .toList();
    }

    @Override
    public List<FacturaResponse> listarFacturasVencidas() {
        LocalDate hoy = LocalDate.now();
        
        return facturaRepository.findBySaldoPendienteGreaterThan(BigDecimal.ZERO)
            .stream()
            .filter(factura -> factura.getFechaVencimiento().isBefore(hoy))
            .map(FacturaResponse::fromDomain)
            .toList();
    }

    @Override
    @Transactional
    public FacturaResponse cambiarEstado(Long facturaId, Long estadoId) {
        // Buscar factura
        Factura factura = facturaRepository.findById(facturaId)
            .orElseThrow(() -> new FacturaNoEncontradaException(facturaId));

        // Buscar nuevo estado
        Estado nuevoEstado = estadoRepository.findById(estadoId)
            .orElseThrow(() -> new EstadoNoEncontradoException(estadoId));

        // Cambiar estado
        factura.cambiarEstado(nuevoEstado);

        // Guardar y retornar
        Factura facturaActualizada = facturaRepository.save(factura);
        return FacturaResponse.fromDomain(facturaActualizada);
    }

    @Override
    @Transactional
    public FacturaResponse anularFactura(Long facturaId) {
        // Buscar factura
        Factura factura = facturaRepository.findById(facturaId)
            .orElseThrow(() -> new FacturaNoEncontradaException(facturaId));

        // Validar que no tenga pagos
        if (factura.getMontoPagado().compareTo(BigDecimal.ZERO) > 0) {
            throw new FacturaAnuladaException("No se puede anular una factura que tiene pagos registrados");
        }

        // Buscar tipo de documento
        TipoDocumento tipoDocumento = tipoDocumentoRepository.findByCodigo("FACTURA")
            .orElseThrow(() -> new TipoDocumentoNoEncontradoException("FACTURA"));

        // Buscar estado "Anulada"
        Estado estadoAnulada = estadoRepository.findByTipoDocumentoId(tipoDocumento.getId())
            .stream()
            .filter(e -> e.getDescripcion().equalsIgnoreCase("Anulada"))
            .findFirst()
            .orElseThrow(() -> new EstadoNoEncontradoException("Anulada", tipoDocumento.getDescripcion()));

        // Cambiar estado
        factura.cambiarEstado(estadoAnulada);

        // Guardar y retornar
        Factura facturaActualizada = facturaRepository.save(factura);
        return FacturaResponse.fromDomain(facturaActualizada);
    }

    @Override
    @Transactional
    public void eliminarFactura(Long id) {
        // Buscar factura
        Factura factura = facturaRepository.findById(id)
            .orElseThrow(() -> new FacturaNoEncontradaException(id));

        // Validar que esté en estado "Anulada"
        if (!factura.getEstado().getDescripcion().equalsIgnoreCase("Anulada")) {
            throw new FacturaAnuladaException(factura.getNumeroFactura());
        }

        // Validar que no tenga pagos
        if (factura.getMontoPagado().compareTo(BigDecimal.ZERO) > 0) {
            throw new FacturaConPagosException(factura.getNumeroFactura());
        }

        facturaRepository.deleteById(id);
    }

    @Override
    @Transactional
    public PagoResponse registrarPago(PagoRequest request) {
        // Buscar factura
        Factura factura = facturaRepository.findById(request.getFacturaId())
            .orElseThrow(() -> new FacturaNoEncontradaException(request.getFacturaId()));

        // Validar que la factura no esté anulada
        if (factura.getEstado().getDescripcion().equalsIgnoreCase("Anulada")) {
            throw new FacturaAnuladaException(factura.getNumeroFactura());
        }

        // Validar que la factura no esté completamente pagada
        if (factura.estaPagada()) {
            throw new FacturaYaPagadaException(factura.getNumeroFactura());
        }

        // Registrar pago en la factura
        factura.registrarPago(request.getMonto());

        // Crear entidad Pago
        Pago pago = new Pago(
            factura,
            request.getMonto(),
            request.getMetodoPago(),
            request.getReferencia()
        );

        // Actualizar estado de la factura según el monto pagado
        actualizarEstadoPorPago(factura);

        // Guardar factura actualizada y pago
        facturaRepository.save(factura);
        Pago pagoGuardado = pagoRepository.save(pago);

        return PagoResponse.fromDomain(pagoGuardado);
    }

    @Override
    public List<PagoResponse> listarPagosPorFactura(Long facturaId) {
        // Validar que existe la factura
        facturaRepository.findById(facturaId)
            .orElseThrow(() -> new FacturaNoEncontradaException(facturaId));

        return pagoRepository.findByFacturaId(facturaId)
            .stream()
            .map(PagoResponse::fromDomain)
            .toList();
    }

    @Override
    public List<PagoResponse> listarPagosPorCliente(Long clienteId) {
        // Validar que existe el cliente
        clienteRepository.findById(clienteId)
            .orElseThrow(() -> new ClienteNoEncontradoException(clienteId));

        return pagoRepository.findByFacturaClienteId(clienteId)
            .stream()
            .map(PagoResponse::fromDomain)
            .toList();
    }

    @Override
    public PagoResponse obtenerPagoPorId(Long id) {
        Pago pago = pagoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + id));
        return PagoResponse.fromDomain(pago);
    }

    // Método auxiliar para actualizar el estado de la factura según los pagos
    private void actualizarEstadoPorPago(Factura factura) {
        TipoDocumento tipoDocumento = tipoDocumentoRepository.findByCodigo("FACTURA")
            .orElseThrow(() -> new TipoDocumentoNoEncontradoException("FACTURA"));

        List<Estado> estados = estadoRepository.findByTipoDocumentoId(tipoDocumento.getId());

        if (factura.estaPagada()) {
            // Cambiar a "Pagada"
            Estado estadoPagada = estados.stream()
                .filter(e -> e.getDescripcion().equalsIgnoreCase("Pagada"))
                .findFirst()
                .orElseThrow(() -> new EstadoNoEncontradoException("Pagada", tipoDocumento.getDescripcion()));
            factura.cambiarEstado(estadoPagada);
        } else if (factura.estaParcialmentePagada()) {
            // Cambiar a "Parcialmente Pagada"
            Estado estadoParcial = estados.stream()
                .filter(e -> e.getDescripcion().equalsIgnoreCase("Parcialmente Pagada"))
                .findFirst()
                .orElseThrow(() -> new EstadoNoEncontradoException("Parcialmente Pagada", tipoDocumento.getDescripcion()));
            factura.cambiarEstado(estadoParcial);
        }
    }
    
    @Override
    public EstadisticasFacturacion obtenerEstadisticas(int anio) {
        // Obtener totales
        BigDecimal totalFacturado = facturaRepository.obtenerTotalFacturadoPorAnio(anio);
        BigDecimal totalPagado = facturaRepository.obtenerTotalPagadoPorAnio(anio);
        BigDecimal totalPendiente = facturaRepository.obtenerTotalPendientePorAnio(anio);
        
        // Obtener contadores
        Long totalFacturas = facturaRepository.contarFacturasPorAnio(anio);
        Long facturasVencidas = facturaRepository.contarFacturasVencidas(anio);
        Long facturasPagadas = facturaRepository.contarFacturasPagadas(anio);
        Long facturasPendientes = facturaRepository.contarFacturasPendientes(anio);
        
        // Obtener ventas por mes
        List<VentasMesDTO> ventasMes = facturaRepository.obtenerVentasPorMes(anio);
        Map<String, BigDecimal> ventasPorMes = convertirVentasMesAMap(ventasMes);
        
        // Obtener top 5 clientes
        List<ClienteTopDTO> topClientes = facturaRepository.obtenerTopClientes(anio, 5);
        
        // Obtener distribución por estado
        Map<String, Long> facturasPorEstado = facturaRepository.obtenerDistribucionPorEstado(anio);
        
        return EstadisticasFacturacion.builder()
                .totalFacturado(totalFacturado)
                .totalPagado(totalPagado)
                .totalPendiente(totalPendiente)
                .totalFacturas(totalFacturas)
                .facturasVencidas(facturasVencidas)
                .facturasPagadas(facturasPagadas)
                .facturasPendientes(facturasPendientes)
                .ventasPorMes(ventasPorMes)
                .topClientes(topClientes)
                .facturasPorEstado(facturasPorEstado)
                .build();
    }
    
    private Map<String, BigDecimal> convertirVentasMesAMap(List<VentasMesDTO> ventasMes) {
        Map<String, BigDecimal> resultado = new LinkedHashMap<>();
        
        // Inicializar todos los meses con 0
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                         "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        
        for (String mes : meses) {
            resultado.put(mes, BigDecimal.ZERO);
        }
        
        // Llenar con los datos reales
        for (VentasMesDTO venta : ventasMes) {
            String nombreMes = meses[venta.getMes() - 1];
            resultado.put(nombreMes, venta.getTotal());
        }
        
        return resultado;
    }
}