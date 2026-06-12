package com.equipo4.pedidosapi.exception;

/**
 * Excepción lanzada cuando un pedido no es encontrado en la base de datos.
 * Extiende RuntimeException para ser una excepción no verificada.
 */
public class PedidoNotFoundException extends RuntimeException {

    public PedidoNotFoundException(String mensaje) {
        super(mensaje);
    }

    public PedidoNotFoundException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    public PedidoNotFoundException(Long idPedido) {
        super("Pedido no encontrado con ID: " + idPedido);
    }
}
