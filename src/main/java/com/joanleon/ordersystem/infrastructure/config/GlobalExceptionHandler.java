package com.joanleon.ordersystem.infrastructure.config;

import com.joanleon.ordersystem.domain.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ==================== NOT FOUND (404) ====================
    
    @ExceptionHandler({
        ClienteNoEncontradoException.class,
        ProductoNoEncontradoException.class,
        PedidoNoEncontradoException.class,
        FacturaNoEncontradaException.class,
        EstadoNoEncontradoException.class,
        TipoDocumentoNoEncontradoException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(DomainException ex, HttpServletRequest request) {
        return ErrorResponse.builder()
            .status(HttpStatus.NOT_FOUND.value())
            .message(ex.getMessage())
            .timestamp(LocalDateTime.now())
            .path(request.getRequestURI())
            .build();
    }

    // ==================== BAD REQUEST (400) ====================
    
    @ExceptionHandler({
        ClienteInactivoException.class,
        StockInsuficienteException.class,
        PedidoSinDetallesException.class,
        EstadoInvalidoParaCancelarException.class,
        EstadoInvalidoParaEliminarException.class,
        FacturaYaPagadaException.class,
        FacturaAnuladaException.class,
        PagoExcedeSaldoException.class,
        PagoInvalidoException.class,
        PedidoNoFacturableException.class,
        FacturaConPagosException.class,
        FechaVencimientoInvalidaException.class,
        EstadoIncompatibleException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequest(DomainException ex, HttpServletRequest request) {
        return ErrorResponse.builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .message(ex.getMessage())
            .timestamp(LocalDateTime.now())
            .path(request.getRequestURI())
            .build();
    }

    // ==================== CONFLICT (409) ====================
    
    @ExceptionHandler({
        EmailDuplicadoException.class,
        CodigoProductoDuplicadoException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflict(DomainException ex, HttpServletRequest request) {
        return ErrorResponse.builder()
            .status(HttpStatus.CONFLICT.value())
            .message(ex.getMessage())
            .timestamp(LocalDateTime.now())
            .path(request.getRequestURI())
            .build();
    }

    // ==================== VALIDATION ERRORS (400) ====================
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationExceptions(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        return ErrorResponse.builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .message("Error de validación en los datos enviados")
            .errors(errors)
            .timestamp(LocalDateTime.now())
            .path(request.getRequestURI())
            .build();
    }

    // ==================== ILLEGAL ARGUMENT (400) ====================
    
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request) {
        return ErrorResponse.builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .message(ex.getMessage())
            .timestamp(LocalDateTime.now())
            .path(request.getRequestURI())
            .build();
    }

    // ==================== GENERIC EXCEPTION (500) ====================
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGenericException(
            Exception ex,
            HttpServletRequest request) {
        
        // Log del error para debugging
        ex.printStackTrace();
        
        return ErrorResponse.builder()
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .message("Error interno del servidor. Por favor contacte al administrador.")
            .timestamp(LocalDateTime.now())
            .path(request.getRequestURI())
            .build();
    }
}