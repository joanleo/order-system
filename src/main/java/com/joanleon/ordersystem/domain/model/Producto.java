package com.joanleon.ordersystem.domain.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Producto {
    private Long id;
    private Integer codigo;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer stock;
}
