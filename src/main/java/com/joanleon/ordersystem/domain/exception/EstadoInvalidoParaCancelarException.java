package com.joanleon.ordersystem.domain.exception;

public class EstadoInvalidoParaCancelarException extends PedidoException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EstadoInvalidoParaCancelarException(String estadoActual) {
        super("No se puede cancelar un pedido en estado: " + estadoActual);
    }
}