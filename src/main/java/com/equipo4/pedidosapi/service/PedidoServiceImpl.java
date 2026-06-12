package com.equipo4.pedidosapi.service;

import com.equipo4.pedidosapi.dto.PedidoMapper;
import com.equipo4.pedidosapi.dto.PedidoRequestDTO;
import com.equipo4.pedidosapi.dto.PedidoResponseDTO;
import com.equipo4.pedidosapi.exception.PedidoNotFoundException;
import com.equipo4.pedidosapi.model.Cliente;
import com.equipo4.pedidosapi.model.Pedido;
import com.equipo4.pedidosapi.repository.ClienteRepository;
import com.equipo4.pedidosapi.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Implementación de la interfaz PedidoService.
 * Contiene la lógica de negocio para la gestión de pedidos.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;

    /**
     * Consulta un pedido por su ID.
     * 
     * @param id el ID del pedido
     * @return PedidoResponseDTO con los datos del pedido
     * @throws PedidoNotFoundException si el pedido no existe
     */
    @Override
    @Transactional(readOnly = true)
    public PedidoResponseDTO consultarPedido(Long id) {
        log.info("Consultando pedido con ID: {}", id);

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Pedido no encontrado con ID: {}", id);
                    return new PedidoNotFoundException(id);
                });

        log.info("Pedido encontrado con ID: {}", id);
        return PedidoMapper.toResponseDTO(pedido);
    }

    /**
     * Crea un nuevo pedido.
     * Si el cliente no existe por nombre y teléfono, lo crea automáticamente.
     * El estado se asigna por defecto como INGRESADO.
     * La fecha estimada de entrega se calcula como ahora + 45 minutos.
     * 
     * @param request el DTO con los datos del pedido a crear
     * @return PedidoResponseDTO con los datos del pedido creado
     */
    @Override
    public PedidoResponseDTO crearPedido(PedidoRequestDTO request) {
        log.info("Creando nuevo pedido para cliente: {}", request.getNombreCliente());

        // Buscar o crear el cliente
        Cliente cliente = clienteRepository.findByNombre(request.getNombreCliente())
                .orElseGet(() -> {
                    log.info("Cliente no encontrado, creando nuevo cliente: {}", request.getNombreCliente());
                    Cliente nuevoCliente = PedidoMapper.toClienteEntity(request);
                    return clienteRepository.save(nuevoCliente);
                });

        // Calcular fecha estimada de entrega: ahora + 45 minutos
        LocalDateTime fechaEstimada = LocalDateTime.now().plusMinutes(45);

        // Crear el pedido
        Pedido pedido = PedidoMapper.toPedidoEntity(request, cliente, fechaEstimada);
        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        log.info("Pedido creado exitosamente con ID: {}", pedidoGuardado.getId());
        return PedidoMapper.toResponseDTO(pedidoGuardado);
    }
}
