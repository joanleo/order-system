package com.joanleon.ordersystem.domain.exception;

public class ClienteInactivoException extends ClienteException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClienteInactivoException(Long id) {
        super("El cliente con ID " + id + " está inactivo");
    }
}