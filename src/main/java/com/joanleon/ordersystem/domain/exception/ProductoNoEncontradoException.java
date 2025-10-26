package com.joanleon.ordersystem.domain.exception;

public class ProductoNoEncontradoException extends ProductoException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProductoNoEncontradoException(Long id) {
        super("Producto no encontrado con ID: " + id);
    }
    
    public ProductoNoEncontradoException(Integer codigo) {
        super("Producto no encontrado con código: " + codigo);
    }
}