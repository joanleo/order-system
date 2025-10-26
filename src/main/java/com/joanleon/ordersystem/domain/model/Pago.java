package com.joanleon.ordersystem.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Pago {
    private Long id;
    private Factura factura;
    private BigDecimal monto;
    private String metodoPago;
    private String referencia;
    private LocalDateTime fechaPago;
    private String observaciones;
    
    public Pago(Factura factura, BigDecimal monto, String metodoPago, String referencia) {
        this.factura = factura;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.referencia = referencia;
        this.fechaPago = LocalDateTime.now();
    }    
    
    void setId(Long id) {
        this.id = id;
    }
}