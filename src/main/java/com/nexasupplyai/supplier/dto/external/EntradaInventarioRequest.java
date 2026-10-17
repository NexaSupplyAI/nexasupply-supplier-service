package com.nexasupplyai.supplier.dto.external;

import java.math.BigDecimal;
import java.util.UUID;

public record EntradaInventarioRequest(
        UUID idAlmacen,
        UUID idProducto,
        BigDecimal cantidad,
        String tipoReferencia,
        UUID idReferencia,
        String claveIdempotencia,
        String motivo,
        String idActor
) {
}
