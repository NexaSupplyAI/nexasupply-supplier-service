package com.nexasupplyai.supplier.dto.request;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record RecepcionMercaderiaRequest(

        @NotBlank
        @Size(max = 40)
        String numeroRecepcion,

        @NotNull
        UUID idOrdenCompra,

        @NotNull
        UUID idAlmacen,

        String notas

) {}
