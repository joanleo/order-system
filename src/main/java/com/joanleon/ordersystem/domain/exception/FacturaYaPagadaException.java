package com.joanleon.ordersystem.domain.exception;

public class FacturaYaPagadaException extends FacturaException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FacturaYaPagadaException(String numeroFactura) {
        super("La factura " + numeroFactura + " ya está completamente pagada");
    }
}