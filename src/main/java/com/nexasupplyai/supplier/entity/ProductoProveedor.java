package com.nexasupplyai.supplier.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "supplier_products",
        schema = "supplier",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_supplier_products_pair",
                        columnNames = {"supplier_id", "product_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class ProductoProveedor {

    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Proveedor proveedor;

    @Column(name = "product_id", nullable = false)
    private UUID idProducto;

    @Column(name = "supplier_sku", length = 100)
    private String skuProveedor;

    @Column(name = "purchase_price", nullable = false, precision = 14, scale = 2)
    private BigDecimal precioCompra;

    @Column(name = "currency", nullable = false, length = 3)
    private String moneda = "PEN";

    @Column(name = "minimum_order_quantity", nullable = false, precision = 14, scale = 3)
    private BigDecimal cantidadMinimaPedido = BigDecimal.ONE;

    @Column(name = "lead_time_days", nullable = false)
    private Integer diasEntrega = 0;

    @Column(name = "preferred", nullable = false)
    private Boolean preferido = false;

    @Column(name = "active", nullable = false)
    private Boolean activo = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime creadoEn;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime actualizadoEn;
}