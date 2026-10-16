package com.nexasupplyai.supplier.exception;

public class DetalleOrdenCompraNoEncontradoException extends RuntimeException {

    public DetalleOrdenCompraNoEncontradoException(Object id) {
        super("Detalle de orden no encontrado: " + id);
    }
}