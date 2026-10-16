package com.nexasupplyai.supplier.exception;

public class HistorialEstadoOrdenCompraNoEncontradoException extends RuntimeException {

    public HistorialEstadoOrdenCompraNoEncontradoException(Object id) {
        super("Historial no encontrado: " + id);
    }
}