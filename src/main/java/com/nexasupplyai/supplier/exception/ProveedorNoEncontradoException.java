package com.nexasupplyai.supplier.exception;

public class ProveedorNoEncontradoException extends RuntimeException {

    public ProveedorNoEncontradoException(Object id) {
        super("Proveedor no encontrado: " + id);
    }
}