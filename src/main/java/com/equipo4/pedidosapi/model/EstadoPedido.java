package com.equipo4.pedidosapi.model;

/**
 * Enum que representa los posibles estados de un pedido en el sistema.
 */
public enum EstadoPedido {
    INGRESADO("Ingresado"),
    EN_PROCESAMIENTO("En Procesamiento"),
    DESPACHADO("Despachado"),
    ENTREGADO("Entregado");

    private final String descripcion;

    EstadoPedido(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
