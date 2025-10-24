package com.joanleon.ordersystem.application.port.out;

import com.joanleon.ordersystem.domain.model.Estado;
import java.util.List;
import java.util.Optional;

public interface EstadoRepositoryPort {
    Optional<Estado> findById(Long id);
    List<Estado> findByTipoDocumentoId(Long tipoDocumentoId);
}