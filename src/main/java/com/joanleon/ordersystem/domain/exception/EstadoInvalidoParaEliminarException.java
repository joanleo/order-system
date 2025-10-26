package com.joanleon.ordersystem.domain.exception;

public class EstadoInvalidoParaEliminarException extends PedidoException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EstadoInvalidoParaEliminarException(String estadoActual) {
        super("Solo se pueden eliminar pedidos en estado Pendiente o Cancelado. Estado actual: " + estadoActual);
    }
}