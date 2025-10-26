package com.joanleon.ordersystem.domain.exception;

public class PagoInvalidoException extends FacturaException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PagoInvalidoException(String mensaje) {
        super(mensaje);
    }
}