package com.nexasupplyai.supplier.dto.response;

import com.nexasupplyai.supplier.enums.EstadoRecepcionMercaderia;
import java.time.OffsetDateTime;
import java.util.UUID;

public record RecepcionMercaderiaResponse(
        UUID id,
        String numeroRecepcion,
        UUID idOrdenCompra,
        UUID idAlmacen,
        EstadoRecepcionMercaderia estado,
        String recibidoPor,
        OffsetDateTime recibidoEn,
        String notas,
        OffsetDateTime creadoEn,
        OffsetDateTime actualizadoEn) {
}
