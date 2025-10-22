package com.joanleon.ordersystem.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private boolean activo;
}
