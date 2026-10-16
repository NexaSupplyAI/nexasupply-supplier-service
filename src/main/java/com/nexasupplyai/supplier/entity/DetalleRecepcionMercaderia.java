package com.nexasupplyai.supplier.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "goods_receipt_items",
        schema = "supplier",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_supplier_receipt_po_item",
                        columnNames = {"goods_receipt_id", "purchase_order_item_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class DetalleRecepcionMercaderia {

    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "goods_receipt_id", nullable = false)
    private RecepcionMercaderia recepcionMercaderia;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "purchase_order_item_id", nullable = false)
    private DetalleOrdenCompra detalleOrdenCompra;

    @Column(name = "product_id", nullable = false)
    private UUID idProducto;

    @Column(name = "quantity_received", nullable = false, precision = 14, scale = 3)
    private BigDecimal cantidadRecibida;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime creadoEn;
}