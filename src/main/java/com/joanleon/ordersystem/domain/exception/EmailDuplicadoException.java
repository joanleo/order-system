package com.joanleon.ordersystem.domain.exception;

public class EmailDuplicadoException extends ClienteException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmailDuplicadoException(String email) {
        super("Ya existe un cliente con el email: " + email);
    }
}