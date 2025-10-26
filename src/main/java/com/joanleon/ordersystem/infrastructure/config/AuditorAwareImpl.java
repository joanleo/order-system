package com.joanleon.ordersystem.infrastructure.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {
    
    @Override
    public Optional<String> getCurrentAuditor() {
        // TODO: Cuando implementes Spring Security, obtener del contexto de seguridad
        // SecurityContext context = SecurityContextHolder.getContext();
        // Authentication authentication = context.getAuthentication();
        // if (authentication != null) {
        //     return Optional.of(authentication.getName());
        // }
        
        // Por ahora, retornar un valor por defecto
        return Optional.of("SYSTEM");
    }
}