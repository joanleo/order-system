package com.joanleon.ordersystem.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TipoDocumento {
    private Long id;
    private String codigo;
    private String descripcion;
}
