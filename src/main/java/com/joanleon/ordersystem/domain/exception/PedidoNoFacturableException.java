package com.joanleon.ordersystem.domain.exception;

public class PedidoNoFacturableException extends FacturaException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PedidoNoFacturableException(String estadoPedido) {
        super("Solo se pueden facturar pedidos en estado 'Entregado'. Estado actual: " + estadoPedido);
    }
}