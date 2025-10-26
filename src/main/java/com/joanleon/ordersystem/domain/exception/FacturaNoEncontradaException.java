package com.joanleon.ordersystem.domain.exception;

public class FacturaNoEncontradaException extends FacturaException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FacturaNoEncontradaException(Long id) {
        super("Factura no encontrada con ID: " + id);
    }
    
    public FacturaNoEncontradaException(String numeroFactura) {
        super("Factura no encontrada con número: " + numeroFactura);
    }
}