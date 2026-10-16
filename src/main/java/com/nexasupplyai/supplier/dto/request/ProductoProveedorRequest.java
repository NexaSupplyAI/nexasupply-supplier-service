package com.nexasupplyai.supplier.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductoProveedorRequest(

        @NotNull
        UUID idProveedor,

        @NotNull
        UUID idProducto,

        @Size(max = 100)
        String skuProveedor,

        @NotNull
        @DecimalMin("0")
        BigDecimal precioCompra,

        @NotBlank
        @Pattern(regexp = "^[A-Z]{3}$")
        String moneda,

        @NotNull
        @DecimalMin(value = "0", inclusive = false)
        BigDecimal cantidadMinimaPedido,

        @NotNull
        @PositiveOrZero
        Integer diasEntrega,

        Boolean preferido,

        Boolean activo

) {}