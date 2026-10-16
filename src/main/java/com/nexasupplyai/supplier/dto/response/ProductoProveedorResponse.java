package com.nexasupplyai.supplier.dto.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record ProductoProveedorResponse(
        UUID id,
        UUID idProveedor,
        UUID idProducto,
        String skuProveedor,
        BigDecimal precioCompra,
        String moneda,
        BigDecimal cantidadMinimaPedido,
        Integer diasEntrega,
        Boolean preferido,
        Boolean activo,
        OffsetDateTime creadoEn,
        OffsetDateTime actualizadoEn) {
}
