package com.nexasupplyai.supplier.entity;

import com.nexasupplyai.supplier.enums.EstadoOrdenCompra;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "purchase_orders", schema = "supplier")
@Getter
@Setter
@NoArgsConstructor
public class OrdenCompra {

    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false)
    private UUID id;

    @Column(name = "purchase_order_number", nullable = false, unique = true, length = 40)
    private String numeroOrdenCompra;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Proveedor proveedor;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private EstadoOrdenCompra estado = EstadoOrdenCompra.BORRADOR;

    @Column(name = "currency", nullable = false, length = 3)
    private String moneda = "PEN";

    @Column(name = "subtotal", nullable = false, precision = 14, scale = 2)
    private BigDecimal subtotal = BigDecimal.ZERO;

    @Column(name = "tax_amount", nullable = false, precision = 14, scale = 2)
    private BigDecimal montoImpuesto = BigDecimal.ZERO;

    @Column(name = "discount_amount", nullable = false, precision = 14, scale = 2)
    private BigDecimal montoDescuento = BigDecimal.ZERO;

    @Column(name = "total_amount", nullable = false, precision = 14, scale = 2)
    private BigDecimal montoTotal = BigDecimal.ZERO;

    @Column(name = "expected_delivery_date")
    private LocalDate fechaEntregaEsperada;

    @Column(name = "notes", columnDefinition = "text")
    private String notas;

    @Column(name = "requested_by", length = 120)
    private String solicitadoPor;

    @Column(name = "approved_by", length = 120)
    private String aprobadoPor;

    @Column(name = "approved_at")
    private OffsetDateTime aprobadoEn;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime creadoEn;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime actualizadoEn;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;
}