package com.joanleon.ordersystem.application.dto;

import lombok.Data;

@Data
public class ClienteRequest {
    private String nombre;
    private String email;
    private String telefono;
    private boolean activo;
}
