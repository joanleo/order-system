package com.joanleon.ordersystem.domain.exception;

public class CodigoProductoDuplicadoException extends ProductoException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CodigoProductoDuplicadoException(Integer codigo) {
        super("Ya existe un producto con el código: " + codigo);
    }
}