package com.joanleon.ordersystem.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

import com.joanleon.ordersystem.domain.model.Pedido;

@Data
@AllArgsConstructor
@Builder
public class PedidoResponse {
	private Long id;
    private String cliente;
    private BigDecimal total;
    private String estadoDescripcion;
    private String tipoDocumento;
    private List<DetallePedidoResponse> detalles;
    
    public static PedidoResponse fromDomain(Pedido pedido) {
        return PedidoResponse.builder()
                .id(pedido.getId())
                .cliente(pedido.getCliente().getNombre())
                .total(pedido.calcularTotal())
                .estadoDescripcion(pedido.getEstado().getDescripcion())
                .tipoDocumento(pedido.getTipoDocumento().getDescripcion())
                .detalles(pedido.getDetalles().stream()
                    .map(DetallePedidoResponse::fromDomain)
                    .toList())
                .build();
    }
}
