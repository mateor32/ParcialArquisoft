package com.equipo4.pedidosapi.repository;

import com.equipo4.pedidosapi.model.Pedido;
import com.equipo4.pedidosapi.model.EstadoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositorio para la entidad Pedido.
 * Proporciona operaciones CRUD y consultas personalizadas.
 */
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    /**
     * Busca todos los pedidos por estado.
     * 
     * @param estado el estado del pedido
     * @return lista de pedidos con ese estado
     */
    List<Pedido> findByEstado(EstadoPedido estado);

    /**
     * Busca todos los pedidos de un cliente.
     * 
     * @param clienteId el ID del cliente
     * @return lista de pedidos del cliente
     */
    List<Pedido> findByClienteId(Long clienteId);

    /**
     * Busca pedidos creados entre dos fechas.
     * 
     * @param fechaInicio fecha inicial (inclusive)
     * @param fechaFin    fecha final (inclusive)
     * @return lista de pedidos creados en ese rango
     */
    List<Pedido> findByFechaCreacionBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    /**
     * Busca pedidos de un cliente con un estado específico.
     * 
     * @param clienteId el ID del cliente
     * @param estado    el estado del pedido
     * @return lista de pedidos que coinciden
     */
    List<Pedido> findByClienteIdAndEstado(Long clienteId, EstadoPedido estado);
}
