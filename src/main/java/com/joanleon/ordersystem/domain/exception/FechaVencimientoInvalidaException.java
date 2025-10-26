package com.joanleon.ordersystem.domain.exception;

public class FechaVencimientoInvalidaException extends FacturaException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FechaVencimientoInvalidaException() {
        super("La fecha de vencimiento no puede ser anterior a la fecha de emisión");
    }
}