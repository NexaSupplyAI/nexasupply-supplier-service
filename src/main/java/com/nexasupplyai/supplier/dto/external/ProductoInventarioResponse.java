package com.nexasupplyai.supplier.dto.external;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Contrato mínimo consumido desde Inventory Service.
 */
public record ProductoInventarioResponse(
        UUID id,
        String sku,
        String nombre,
        BigDecimal precioVenta,
        Boolean activo
) {
}
