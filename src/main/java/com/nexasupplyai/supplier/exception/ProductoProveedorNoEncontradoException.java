package com.nexasupplyai.supplier.exception;

public class ProductoProveedorNoEncontradoException extends RuntimeException {

    public ProductoProveedorNoEncontradoException(Object id) {
        super("Producto de proveedor no encontrado: " + id);
    }
}