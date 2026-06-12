package com.equipo4.pedidosapi.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * DTO para respuestas de error estándar.
 */
@Getter
@Builder
public class ErrorResponse {

    private int status;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    private String mensaje;

    private String detalle;

    @Builder.Default
    private LocalDateTime timestamp_response = LocalDateTime.now();
}
