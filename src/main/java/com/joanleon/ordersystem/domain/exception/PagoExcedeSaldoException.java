package com.joanleon.ordersystem.domain.exception;

import java.math.BigDecimal;

public class PagoExcedeSaldoException extends FacturaException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PagoExcedeSaldoException(BigDecimal monto, BigDecimal saldo) {
        super(String.format("El monto $%,.2f excede el saldo pendiente de $%,.2f", 
            monto, saldo));
    }
}