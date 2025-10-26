package com.joanleon.ordersystem.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class Factura {
    private Long id;
    private String numeroFactura;
    private Cliente cliente;
    private Pedido pedido; // Opcional - puede ser null si es factura directa
    private List<DetalleFactura> detalles;
    private Estado estado;
    private TipoDocumento tipoDocumento;
    
    // Fechas
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;
    private LocalDateTime fechaCreacion;
    
    // Montos
    private BigDecimal subtotal;
    private BigDecimal descuento;
    private BigDecimal iva;
    private BigDecimal otrosImpuestos;
    private BigDecimal total;
    
    // Información adicional
    private String metodoPago;
    private String terminosCondiciones;
    private String observaciones;
    
    // Control de pagos
    private BigDecimal montoPagado;
    private BigDecimal saldoPendiente;

    // Constructor desde pedido
    public Factura(Pedido pedido, String numeroFactura, LocalDate fechaEmision, 
                   LocalDate fechaVencimiento, Estado estadoInicial, 
                   TipoDocumento tipoDocumento, BigDecimal descuento, 
                   BigDecimal tasaIva, String metodoPago) {
        this.pedido = pedido;
        this.cliente = pedido.getCliente();
        this.numeroFactura = numeroFactura;
        this.fechaEmision = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
        this.estado = estadoInicial;
        this.tipoDocumento = tipoDocumento;
        this.metodoPago = metodoPago;
        this.fechaCreacion = LocalDateTime.now();
        this.montoPagado = BigDecimal.ZERO;
        
        // Convertir detalles del pedido a detalles de factura
        this.detalles = pedido.getDetalles().stream()
            .map(dp -> new DetalleFactura(dp.getProducto(), dp.getCantidad()))
            .toList();
        
        calcularMontos(descuento, tasaIva);
    }
    
    // Constructor manual (sin pedido)
    public Factura(Cliente cliente, List<DetalleFactura> detalles, 
                   String numeroFactura, LocalDate fechaEmision, 
                   LocalDate fechaVencimiento, Estado estadoInicial,
                   TipoDocumento tipoDocumento, BigDecimal descuento, 
                   BigDecimal tasaIva, String metodoPago) {
        this.cliente = cliente;
        this.detalles = detalles;
        this.numeroFactura = numeroFactura;
        this.fechaEmision = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
        this.estado = estadoInicial;
        this.tipoDocumento = tipoDocumento;
        this.metodoPago = metodoPago;
        this.fechaCreacion = LocalDateTime.now();
        this.montoPagado = BigDecimal.ZERO;
        
        calcularMontos(descuento, tasaIva);
    }
    
    /**
     * Método factory para reconstruir Factura desde la capa de persistencia
     */
    public static Factura reconstruirDesdeDB(
            Long id,
            String numeroFactura,
            Cliente cliente,
            Pedido pedido,
            List<DetalleFactura> detalles,
            Estado estado,
            TipoDocumento tipoDocumento,
            LocalDate fechaEmision,
            LocalDate fechaVencimiento,
            LocalDateTime fechaCreacion,
            BigDecimal subtotal,
            BigDecimal descuento,
            BigDecimal iva,
            BigDecimal otrosImpuestos,
            BigDecimal total,
            String metodoPago,
            String terminosCondiciones,
            String observaciones,
            BigDecimal montoPagado,
            BigDecimal saldoPendiente
    ) {
        Factura factura = new Factura();
        factura.id = id;
        factura.numeroFactura = numeroFactura;
        factura.cliente = cliente;
        factura.pedido = pedido;
        factura.detalles = detalles;
        factura.estado = estado;
        factura.tipoDocumento = tipoDocumento;
        factura.fechaEmision = fechaEmision;
        factura.fechaVencimiento = fechaVencimiento;
        factura.fechaCreacion = fechaCreacion;
        factura.subtotal = subtotal;
        factura.descuento = descuento;
        factura.iva = iva;
        factura.otrosImpuestos = otrosImpuestos;
        factura.total = total;
        factura.metodoPago = metodoPago;
        factura.terminosCondiciones = terminosCondiciones;
        factura.observaciones = observaciones;
        factura.montoPagado = montoPagado;
        factura.saldoPendiente = saldoPendiente;
        return factura;
    }
    
    // Constructor privado sin argumentos para el factory method
    private Factura() {
    }
    
    private void calcularMontos(BigDecimal descuento, BigDecimal tasaIva) {
        // Calcular subtotal
        this.subtotal = detalles.stream()
            .map(DetalleFactura::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // Aplicar descuento
        this.descuento = descuento != null ? descuento : BigDecimal.ZERO;
        BigDecimal subtotalConDescuento = this.subtotal.subtract(this.descuento);
        
        // Calcular IVA
        this.iva = subtotalConDescuento.multiply(tasaIva != null ? tasaIva : BigDecimal.ZERO);
        
        // Total
        this.total = subtotalConDescuento.add(this.iva);
        this.saldoPendiente = this.total;
    }
    
    public void registrarPago(BigDecimal monto) {
        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }
        if (monto.compareTo(this.saldoPendiente) > 0) {
            throw new IllegalArgumentException("El monto excede el saldo pendiente");
        }
        
        this.montoPagado = this.montoPagado.add(monto);
        this.saldoPendiente = this.saldoPendiente.subtract(monto);
    }
    
    public void cambiarEstado(Estado nuevoEstado) {
        if (!nuevoEstado.getTipoDocumento().getId().equals(this.tipoDocumento.getId())) {
            throw new IllegalArgumentException("El estado no pertenece al tipo de documento");
        }
        this.estado = nuevoEstado;
    }
    
    public boolean estaPagada() {
        return this.saldoPendiente.compareTo(BigDecimal.ZERO) == 0;
    }
    
    public boolean estaParcialmentePagada() {
        return this.montoPagado.compareTo(BigDecimal.ZERO) > 0 
            && this.saldoPendiente.compareTo(BigDecimal.ZERO) > 0;
    }
    
    void setId(Long id) {
        this.id = id;
    }
}