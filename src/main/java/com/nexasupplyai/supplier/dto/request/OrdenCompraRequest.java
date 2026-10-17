package com.nexasupplyai.supplier.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record OrdenCompraRequest(

        @NotBlank
        @Size(max = 40)
        String numeroOrdenCompra,

        @NotNull
        UUID idProveedor,

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
        String solicitadoPor

) {}
