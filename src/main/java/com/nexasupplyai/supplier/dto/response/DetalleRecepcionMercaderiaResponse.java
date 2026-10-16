package com.nexasupplyai.supplier.dto.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record DetalleRecepcionMercaderiaResponse(
        UUID id,
        UUID idRecepcionMercaderia,
        UUID idDetalleOrdenCompra,
        UUID idProducto,
        BigDecimal cantidadRecibida,
        OffsetDateTime creadoEn) {
}
