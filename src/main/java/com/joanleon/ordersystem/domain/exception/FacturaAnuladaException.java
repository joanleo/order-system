package com.joanleon.ordersystem.domain.exception;

public class FacturaAnuladaException extends FacturaException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FacturaAnuladaException(String numeroFactura) {
        super("No se pueden realizar operaciones en la factura anulada: " + numeroFactura);
    }
}