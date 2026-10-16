package com.nexasupplyai.supplier.exception;

public class RecepcionMercaderiaNoEncontradaException extends RuntimeException {

    public RecepcionMercaderiaNoEncontradaException(Object id) {
        super("Recepción no encontrada: " + id);
    }
}