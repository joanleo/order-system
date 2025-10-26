package com.joanleon.ordersystem.domain.exception;

public class FacturaConPagosException extends FacturaException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FacturaConPagosException(String numeroFactura) {
        super("No se puede anular/eliminar la factura " + numeroFactura + " porque tiene pagos registrados");
    }
}