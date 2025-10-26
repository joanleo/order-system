package com.joanleon.ordersystem.domain.exception;

public class PedidoNoEncontradoException extends PedidoException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PedidoNoEncontradoException(Long id) {
        super("Pedido no encontrado con ID: " + id);
    }
}