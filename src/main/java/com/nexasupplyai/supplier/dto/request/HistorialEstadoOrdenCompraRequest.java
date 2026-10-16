package com.nexasupplyai.supplier.dto.request;

import com.nexasupplyai.supplier.enums.EstadoOrdenCompra;
import jakarta.validation.constraints.*;

import java.time.OffsetDateTime;
import java.util.UUID;

public record HistorialEstadoOrdenCompraRequest(

        @NotNull
        UUID idOrdenCompra,

        EstadoOrdenCompra estadoAnterior,

        @NotNull
        EstadoOrdenCompra nuevoEstado,

        @Size(max = 300)
        String motivo,

        @Size(max = 120)
        String cambiadoPor,

        OffsetDateTime cambiadoEn

) {}