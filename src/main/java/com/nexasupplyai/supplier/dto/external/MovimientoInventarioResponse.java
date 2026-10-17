package com.nexasupplyai.supplier.dto.external;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

public record MovimientoInventarioResponse(UUID id, UUID idAlmacen, UUID idProducto, String tipoMovimiento,
        BigDecimal cantidad, BigDecimal saldoAnterior, BigDecimal saldoPosterior, String tipoReferencia,
        UUID idReferencia, String claveIdempotencia, String motivo, String idActor,
        Map<String, Object> metadatos, OffsetDateTime creadoEn) { }
