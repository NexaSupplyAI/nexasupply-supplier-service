package com.nexasupplyai.supplier.dto.request;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

public record DetalleOrdenCompraRequest(

                @NotNull UUID idOrdenCompra,

                @NotNull UUID idProducto,

                @NotBlank @Size(max = 80) String skuInstantanea,

                @NotBlank @Size(max = 180) String nombreProductoInstantanea,

                @NotNull @DecimalMin(value = "0", inclusive = false) BigDecimal cantidadPedida,

                @NotNull @DecimalMin("0") BigDecimal precioUnitario,

                @NotNull @DecimalMin("0") BigDecimal montoDescuento

) {
}
