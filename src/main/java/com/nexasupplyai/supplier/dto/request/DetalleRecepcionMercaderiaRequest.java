package com.nexasupplyai.supplier.dto.request;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

public record DetalleRecepcionMercaderiaRequest(

        @NotNull
        UUID idRecepcionMercaderia,

        @NotNull
        UUID idDetalleOrdenCompra,

        @NotNull
        UUID idProducto,

        @NotNull
        @DecimalMin(value = "0", inclusive = false)
        BigDecimal cantidadRecibida

) {}