package com.equipo4.pedidosapi.service;

import com.equipo4.pedidosapi.dto.PedidoRequestDTO;
import com.equipo4.pedidosapi.dto.PedidoResponseDTO;

/**
 * Interfaz que define la lógica de negocio para pedidos.
 */
public interface PedidoService {

    /**
     * Consulta un pedido por su ID.
     * 
     * @param id el ID del pedido
     * @return PedidoResponseDTO con los datos del pedido
     * @throws com.equipo4.pedidosapi.exception.PedidoNotFoundException si el pedido
     *                                                                  no existe
     */
    PedidoResponseDTO consultarPedido(Long id);

    /**
     * Crea un nuevo pedido.
     * Si el cliente no existe, lo crea automáticamente.
     * 
     * @param request el DTO con los datos del pedido a crear
     * @return PedidoResponseDTO con los datos del pedido creado
     */
    PedidoResponseDTO crearPedido(PedidoRequestDTO request);
}
