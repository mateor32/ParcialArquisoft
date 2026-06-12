package com.equipo4.pedidosapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad que representa un pedido realizado por un cliente.
 * Contiene información sobre el estado, fechas y monto total del pedido.
 */
@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    @NotNull(message = "El pedido debe estar asociado a un cliente")
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private EstadoPedido estado = EstadoPedido.INGRESADO;

    @NotNull(message = "La fecha estimada de entrega no puede ser nula")
    @Column(nullable = false)
    private LocalDateTime fechaHoraEstimadaEntrega;

    @NotBlank(message = "La descripción del pedido no puede estar vacía")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String descripcion;

    @NotNull(message = "El total del pedido no puede ser nulo")
    @DecimalMin(value = "0.01", message = "El total debe ser mayor a 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    /**
     * Pre-persist: establece la fecha de creación antes de guardar la entidad
     */
    @PrePersist
    public void prePersist() {
        if (this.fechaCreacion == null) {
            this.fechaCreacion = LocalDateTime.now();
        }
        if (this.estado == null) {
            this.estado = EstadoPedido.INGRESADO;
        }
    }
}
