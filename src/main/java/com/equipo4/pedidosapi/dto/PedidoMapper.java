package com.equipo4.pedidosapi.dto;

import com.equipo4.pedidosapi.model.Pedido;
import com.equipo4.pedidosapi.model.Cliente;

import java.math.BigDecimal;

/**
 * Clase utilitaria para mapear entre entidades y DTOs.
 * Proporciona métodos estáticos para conversiones.
 */
public class PedidoMapper {

    /**
     * Convierte una entidad Pedido a PedidoResponseDTO.
     * 
     * @param pedido la entidad Pedido
     * @return PedidoResponseDTO con los datos del pedido
     */
    public static PedidoResponseDTO toResponseDTO(Pedido pedido) {
        if (pedido == null) {
            return null;
        }

        return PedidoResponseDTO.builder()
                .id(pedido.getId())
                .estado(pedido.getEstado().getDescripcion())
                .nombreCliente(pedido.getCliente().getNombre())
                .direccion(pedido.getCliente().getDireccion())
                .fechaHoraEstimadaEntrega(pedido.getFechaHoraEstimadaEntrega())
                .descripcion(pedido.getDescripcion())
                .total(pedido.getTotal().toPlainString())
                .fechaCreacion(pedido.getFechaCreacion())
                .build();
    }

    /**
     * Convierte un PedidoRequestDTO a una entidad Pedido.
     * Se requiere pasar el cliente asociado.
     * 
     * @param requestDTO           el DTO de solicitud
     * @param cliente              el cliente asociado al pedido
     * @param fechaEstimadaEntrega la fecha estimada de entrega
     * @return entidad Pedido con los datos del DTO
     */
    public static Pedido toPedidoEntity(
            PedidoRequestDTO requestDTO,
            Cliente cliente,
            java.time.LocalDateTime fechaEstimadaEntrega) {

        if (requestDTO == null || cliente == null) {
            return null;
        }

        return Pedido.builder()
                .cliente(cliente)
                .descripcion(requestDTO.getDescripcion())
                .total(requestDTO.getTotal())
                .fechaHoraEstimadaEntrega(fechaEstimadaEntrega)
                .build();
    }

    /**
     * Convierte un PedidoRequestDTO a una entidad Cliente.
     * 
     * @param requestDTO el DTO de solicitud
     * @return entidad Cliente con los datos del DTO
     */
    public static Cliente toClienteEntity(PedidoRequestDTO requestDTO) {
        if (requestDTO == null) {
            return null;
        }

        return Cliente.builder()
                .nombre(requestDTO.getNombreCliente())
                .direccion(requestDTO.getDireccion())
                .telefono(requestDTO.getTelefono())
                .build();
    }
}
