package com.joanleon.ordersystem.domain.exception;

public class TipoDocumentoNoEncontradoException extends TipoDocumentoException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TipoDocumentoNoEncontradoException(Long id) {
        super("Tipo de documento no encontrado con ID: " + id);
    }
    
    public TipoDocumentoNoEncontradoException(String codigo) {
        super("Tipo de documento no encontrado con código: " + codigo);
    }
}