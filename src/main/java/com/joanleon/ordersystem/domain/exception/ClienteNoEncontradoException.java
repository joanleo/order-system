package com.joanleon.ordersystem.domain.exception;

// Excepciones específicas de Cliente
public class ClienteNoEncontradoException extends ClienteException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClienteNoEncontradoException(Long id) {
        super("Cliente no encontrado con ID: " + id);
    }
}