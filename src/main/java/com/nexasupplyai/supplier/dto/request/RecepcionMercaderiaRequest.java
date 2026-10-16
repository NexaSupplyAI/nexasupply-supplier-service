package com.nexasupplyai.supplier.dto.request;

import com.nexasupplyai.supplier.enums.EstadoRecepcionMercaderia;
import jakarta.validation.constraints.*;

import java.time.OffsetDateTime;
import java.util.UUID;

public record RecepcionMercaderiaRequest(

        @NotBlank
        @Size(max = 40)
        String numeroRecepcion,

        @NotNull
        UUID idOrdenCompra,

        @NotNull
        UUID idAlmacen,

        EstadoRecepcionMercaderia estado,

        @Size(max = 120)
        String recibidoPor,

        OffsetDateTime recibidoEn,

        String notas

) {}