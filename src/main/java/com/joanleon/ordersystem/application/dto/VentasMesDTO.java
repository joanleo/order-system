package com.joanleon.ordersystem.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentasMesDTO {
    private Integer mes;
    private Integer anio;
    private BigDecimal total;
}