package com.joanleon.ordersystem.domain.exception;

public class EstadoNoEncontradoException extends EstadoException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EstadoNoEncontradoException(Long id) {
        super("Estado no encontrado con ID: " + id);
    }
    
    public EstadoNoEncontradoException(String descripcion, String tipoDocumento) {
        super(String.format("Estado '%s' no encontrado para el tipo de documento '%s'", 
            descripcion, tipoDocumento));
    }
}