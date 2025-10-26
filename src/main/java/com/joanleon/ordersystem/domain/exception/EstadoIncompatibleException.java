package com.joanleon.ordersystem.domain.exception;

public class EstadoIncompatibleException extends EstadoException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EstadoIncompatibleException(String mensaje) {
        super(mensaje);
    }
}