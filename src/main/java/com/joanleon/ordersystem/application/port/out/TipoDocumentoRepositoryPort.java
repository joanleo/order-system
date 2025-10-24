package com.joanleon.ordersystem.application.port.out;

import com.joanleon.ordersystem.domain.model.TipoDocumento;
import java.util.Optional;

public interface TipoDocumentoRepositoryPort {
    Optional<TipoDocumento> findById(Long id);
    Optional<TipoDocumento> findByCodigo(String codigo);
}
