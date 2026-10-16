package com.nexasupplyai.supplier.exception;

public class DetalleRecepcionMercaderiaNoEncontradoException extends RuntimeException {

    public DetalleRecepcionMercaderiaNoEncontradoException(Object id) {
        super("Detalle de recepción no encontrado: " + id);
    }
}