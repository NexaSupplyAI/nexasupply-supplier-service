package com.nexasupplyai.supplier.dto.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record DetalleOrdenCompraResponse(
        UUID id,
        UUID idOrdenCompra,
        UUID idProducto,
        String skuInstantanea,
        String nombreProductoInstantanea,
        BigDecimal cantidadPedida,
        BigDecimal cantidadRecibida,
        BigDecimal precioUnitario,
        BigDecimal montoDescuento,
        BigDecimal totalLinea,
        OffsetDateTime creadoEn) {
}
