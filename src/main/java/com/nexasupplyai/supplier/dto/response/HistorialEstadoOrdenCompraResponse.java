package com.nexasupplyai.supplier.dto.response;

import com.nexasupplyai.supplier.enums.EstadoOrdenCompra;
import java.time.OffsetDateTime;
import java.util.UUID;

public record HistorialEstadoOrdenCompraResponse(
        UUID id,
        UUID idOrdenCompra,
        EstadoOrdenCompra estadoAnterior,
        EstadoOrdenCompra nuevoEstado,
        String motivo,
        String cambiadoPor,
        OffsetDateTime cambiadoEn) {
}
