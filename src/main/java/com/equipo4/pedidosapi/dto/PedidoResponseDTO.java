package com.equipo4.pedidosapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO para la respuesta de un pedido.
 * Se utiliza en las respuestas GET.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoResponseDTO {

    @JsonProperty("idPedido")
    private Long id;

    @JsonProperty("estado")
    private String estado;

    @JsonProperty("nombreCliente")
    private String nombreCliente;

    @JsonProperty("direccion")
    private String direccion;

    @JsonProperty("fechaHoraEstimadaEntrega")
    private LocalDateTime fechaHoraEstimadaEntrega;

    @JsonProperty("descripcion")
    private String descripcion;

    @JsonProperty("total")
    private String total;

    @JsonProperty("fechaCreacion")
    private LocalDateTime fechaCreacion;
}
