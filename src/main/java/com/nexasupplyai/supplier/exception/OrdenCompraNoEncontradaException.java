package com.nexasupplyai.supplier.exception;

public class OrdenCompraNoEncontradaException extends RuntimeException {

    public OrdenCompraNoEncontradaException(Object id) {
        super("Orden de compra no encontrada: " + id);
    }
}