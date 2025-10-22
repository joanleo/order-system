package com.joanleon.ordersystem.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

import com.joanleon.ordersystem.domain.model.Pedido;

@Data
@AllArgsConstructor
public class PedidoResponse {
    private Long id;
    private String cliente;
    private BigDecimal total;

    public static PedidoResponse fromDomain(Pedido pedido) {
        return new PedidoResponse(
                pedido.getId(),
                pedido.getCliente().getNombre(),
                pedido.calcularTotal()
        );
    }
}
