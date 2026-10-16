package com.nexasupplyai.supplier.dto.request;

import com.nexasupplyai.supplier.enums.EstadoProveedor;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProveedorRequest(

        @NotBlank
        @Size(max = 40)
        String codigoProveedor,

        @Size(max = 30)
        String numeroFiscal,

        @NotBlank
        @Size(max = 180)
        String razonSocial,

        @Size(max = 180)
        String nombreComercial,

        @Email
        @Size(max = 180)
        String email,

        @Size(max = 50)
        String telefono,

        EstadoProveedor estado,

        @DecimalMin("0")
        @DecimalMax("5")
        BigDecimal calificacion,

        @NotNull
        @PositiveOrZero
        Integer diasEntregaPredeterminados,

        @Size(max = 220)
        String direccion,

        @Size(max = 120)
        String distrito,

        @Size(max = 120)
        String ciudad,

        @Size(max = 120)
        String region,

        @NotBlank
        @Pattern(regexp = "^[A-Z]{2}$")
        String codigoPais

) {}