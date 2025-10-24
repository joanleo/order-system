package com.joanleon.ordersystem.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Estado {
    private Long id;
    private String descripcion;
    private TipoDocumento tipoDocumento;
}
