package com.joanleon.ordersystem.infrastructure.adapter.out.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "facturas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacturaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 50)
    private String numeroFactura;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteEntity cliente;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pedido_id")
    private PedidoEntity pedido;
    
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleFacturaEntity> detalles;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estado_id", nullable = false)
    private EstadoEntity estado;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_documento_id", nullable = false)
    private TipoDocumentoEntity tipoDocumento;
    
    @Column(nullable = false)
    private LocalDate fechaEmision;
    
    @Column(nullable = false)
    private LocalDate fechaVencimiento;
    
    @Column(nullable = false)
    private LocalDateTime fechaCreacion;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal descuento;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal iva;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal otrosImpuestos;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;
    
    @Column(length = 50)
    private String metodoPago;
    
    @Column(length = 500)
    private String terminosCondiciones;
    
    @Column(length = 500)
    private String observaciones;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal montoPagado;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal saldoPendiente;
}