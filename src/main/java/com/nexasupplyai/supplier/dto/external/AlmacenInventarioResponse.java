package com.nexasupplyai.supplier.dto.external;

import java.util.UUID;

/**
 * Contrato mínimo consumido desde Inventory Service.
 */
public record AlmacenInventarioResponse(
        UUID id,
        String codigo,
        String nombre,
        Boolean activo
) {
}
