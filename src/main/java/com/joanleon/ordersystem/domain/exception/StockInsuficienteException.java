package com.joanleon.ordersystem.domain.exception;

public class StockInsuficienteException extends ProductoException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StockInsuficienteException(String nombreProducto, int stockDisponible, int cantidadRequerida) {
        super(String.format("Stock insuficiente para '%s'. Disponible: %d, Requerido: %d", 
            nombreProducto, stockDisponible, cantidadRequerida));
    }
}