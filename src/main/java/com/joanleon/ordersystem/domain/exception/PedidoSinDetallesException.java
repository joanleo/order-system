package com.joanleon.ordersystem.domain.exception;

public class PedidoSinDetallesException extends PedidoException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PedidoSinDetallesException() {
        super("El pedido debe tener al menos un detalle");
    }
}