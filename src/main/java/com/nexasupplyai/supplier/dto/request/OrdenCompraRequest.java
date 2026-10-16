package com.nexasupplyai.supplier.dto.request;

import com.nexasupplyai.supplier.enums.EstadoOrdenCompra;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record OrdenCompraRequest(

        @NotBlank
        @Size(max = 40)
        String numeroOrdenCompra,

        @NotNull
        UUID idProveedor,

        EstadoOrdenCompra estado,

        @NotBlank
        @Pattern(regexp = "^[A-Z]{3}$")
        String moneda,

        @NotNull
        @DecimalMin("0")
        BigDecimal subtotal,

        @NotNull
        @DecimalMin("0")
        BigDecimal montoImpuesto,

        @NotNull
        @DecimalMin("0")
        BigDecimal montoDescuento,

        @NotNull
        @DecimalMin("0")
        BigDecimal montoTotal,

        LocalDate fechaEntregaEsperada,

        String notas,

        @Size(max = 120)
        String solicitadoPor,

        @Size(max = 120)
        String aprobadoPor,

        OffsetDateTime aprobadoEn

) {}