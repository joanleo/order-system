package com.joanleon.ordersystem.infrastructure.adapter.out.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "factura_id", nullable = false)
    private FacturaEntity factura;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;
    
    @Column(nullable = false, length = 50)
    private String metodoPago;
    
    @Column(length = 100)
    private String referencia;
    
    @Column(nullable = false)
    private LocalDateTime fechaPago;
    
    @Column(length = 500)
    private String observaciones;
}