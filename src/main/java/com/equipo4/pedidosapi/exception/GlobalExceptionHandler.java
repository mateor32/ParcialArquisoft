package com.equipo4.pedidosapi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para la API REST.
 * Centraliza el manejo de errores y proporciona respuestas consistentes en
 * JSON.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Maneja la excepción PedidoNotFoundException.
     * Retorna 404 Not Found.
     */
    @ExceptionHandler(PedidoNotFoundException.class)
    public ResponseEntity<ErrorResponse> manejarPedidoNoEncontrado(
            PedidoNotFoundException ex,
            WebRequest request) {

        log.error("PedidoNotFoundException: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .mensaje(ex.getMessage())
                .detalle("El recurso solicitado no existe")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja errores de validación (MethodArgumentNotValidException).
     * Retorna 400 Bad Request con detalle de los campos inválidos.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarValidationException(
            MethodArgumentNotValidException ex,
            WebRequest request) {

        log.error("MethodArgumentNotValidException: Errores de validación detectados");

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("timestamp", LocalDateTime.now());
        response.put("mensaje", "Errores de validación");

        Map<String, String> erroresDetalle = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> erroresDetalle.put(error.getField(), error.getDefaultMessage()));

        response.put("errores", erroresDetalle);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja todas las demás excepciones no capturadas.
     * Retorna 500 Internal Server Error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarExceptionGenerica(
            Exception ex,
            WebRequest request) {

        log.error("Exception no controlada: ", ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now())
                .mensaje("Error interno del servidor")
                .detalle(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
