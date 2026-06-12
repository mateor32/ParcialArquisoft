package com.equipo4.pedidosapi.controller;

import com.equipo4.pedidosapi.dto.PedidoRequestDTO;
import com.equipo4.pedidosapi.dto.PedidoResponseDTO;
import com.equipo4.pedidosapi.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * Controlador REST para la gestión de pedidos.
 * Implementa versionamiento por Accept Header usando
 * "application/vnd.equipo4.v1+json".
 * Usa Spring HATEOAS para incluir enlaces en las respuestas.
 */
@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "Pedidos", description = "Endpoints para la gestión de pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    /**
     * Obtiene un pedido por su ID.
     * 
     * @param id el ID del pedido
     * @return EntityModel con PedidoResponseDTO y enlaces HATEOAS
     */
    @GetMapping(value = "/{id}", produces = { "application/vnd.equipo4.v1+json", "application/json" })
    @Operation(summary = "Obtener un pedido", description = "Retorna los detalles de un pedido específico por su ID", tags = {
            "Pedidos" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado", content = @Content(mediaType = "application/vnd.equipo4.v1+json", schema = @Schema(implementation = PedidoResponseDTO.class), examples = @ExampleObject(name = "Ejemplo de respuesta", value = """
                    {
                      "idPedido": 1,
                      "estado": "Ingresado",
                      "nombreCliente": "Juan Pérez",
                      "direccion": "Calle Principal 123",
                      "fechaHoraEstimadaEntrega": "2026-06-12T15:30:00",
                      "descripcion": "2x Producto A, 1x Producto B",
                      "total": "150.50",
                      "fechaCreacion": "2026-06-12T14:45:00"
                    }
                    """))),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<EntityModel<PedidoResponseDTO>> obtenerPedido(
            @PathVariable @Parameter(description = "ID del pedido", example = "1") Long id) {

        log.info("GET /api/pedidos/{} - Obtener pedido", id);

        PedidoResponseDTO pedidoDTO = pedidoService.consultarPedido(id);

        // Crear EntityModel con el DTO
        EntityModel<PedidoResponseDTO> model = EntityModel.of(pedidoDTO);

        // Agregar enlace self (GET al mismo recurso)
        model.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(PedidoController.class).obtenerPedido(id))
                .withSelfRel());

        // Agregar enlace a la colección de pedidos
        model.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(PedidoController.class).listarPedidos())
                .withRel("all-pedidos"));

        // Si el estado es INGRESADO, agregar enlace para cancelar
        if ("Ingresado".equals(pedidoDTO.getEstado())) {
            model.add(Link.of(
                    "/api/pedidos/" + id + "/cancelar",
                    "cancelar"));
        }

        log.info("Pedido {} retornado exitosamente", id);
        return ResponseEntity.ok(model);
    }

    /**
     * Crea un nuevo pedido.
     * 
     * @param request el DTO con los datos del pedido a crear
     * @return ResponseEntity con código 201 Created y el recurso creado
     */
    @PostMapping(consumes = { "application/vnd.equipo4.v1+json", "application/json" }, produces = {
            "application/vnd.equipo4.v1+json", "application/json" })
    @Operation(summary = "Crear un nuevo pedido", description = "Crea un nuevo pedido. Si el cliente no existe, se crea automáticamente.", tags = {
            "Pedidos" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido creado exitosamente", content = @Content(mediaType = "application/vnd.equipo4.v1+json", schema = @Schema(implementation = PedidoResponseDTO.class), examples = @ExampleObject(name = "Ejemplo de respuesta", value = """
                    {
                      "idPedido": 1,
                      "estado": "Ingresado",
                      "nombreCliente": "Juan Pérez",
                      "direccion": "Calle Principal 123",
                      "fechaHoraEstimadaEntrega": "2026-06-12T15:30:00",
                      "descripcion": "2x Producto A, 1x Producto B",
                      "total": "150.50",
                      "fechaCreacion": "2026-06-12T14:45:00"
                    }
                    """))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EntityModel<PedidoResponseDTO>> crearPedido(
            @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del pedido a crear", required = true, content = @Content(mediaType = "application/vnd.equipo4.v1+json", schema = @Schema(implementation = PedidoRequestDTO.class), examples = @ExampleObject(name = "Ejemplo de solicitud", value = """
                    {
                      "nombreCliente": "Juan Pérez",
                      "direccion": "Calle Principal 123",
                      "telefono": "555-123456",
                      "descripcion": "2x Producto A, 1x Producto B",
                      "total": 150.50
                    }
                    """))) PedidoRequestDTO request) {

        log.info("POST /api/pedidos - Crear pedido para cliente: {}", request.getNombreCliente());

        PedidoResponseDTO pedidoCreado = pedidoService.crearPedido(request);

        // Crear EntityModel con el DTO
        EntityModel<PedidoResponseDTO> model = EntityModel.of(pedidoCreado);

        // Agregar enlace self (GET al recurso creado)
        model.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(PedidoController.class).obtenerPedido(pedidoCreado.getId()))
                .withSelfRel());

        // Agregar enlace a la colección de pedidos
        model.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(PedidoController.class).listarPedidos())
                .withRel("all-pedidos"));

        log.info("Pedido creado exitosamente con ID: {}", pedidoCreado.getId());

        // Retornar 201 Created con el header Location
        return ResponseEntity
                .created(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(PedidoController.class)
                                .obtenerPedido(pedidoCreado.getId()))
                        .toUri())
                .body(model);
    }

    /**
     * Endpoint auxiliar para listar pedidos (utilizado en los enlaces HATEOAS).
     * Este método es principalmente para generar URIs en HATEOAS.
     * 
     * @return ResponseEntity sin contenido
     */
    @GetMapping(produces = { "application/vnd.equipo4.v1+json", "application/json" })
    @Operation(hidden = true)
    public ResponseEntity<Void> listarPedidos() {
        return ResponseEntity.ok().build();
    }
}
