package com.nexasupplyai.supplier.entity;

import com.nexasupplyai.supplier.enums.EstadoRecepcionMercaderia;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "goods_receipts", schema = "supplier")
@Getter
@Setter
@NoArgsConstructor
public class RecepcionMercaderia {

    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false)
    private UUID id;

    @Column(name = "receipt_number", nullable = false, unique = true, length = 40)
    private String numeroRecepcion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "purchase_order_id", nullable = false)
    private OrdenCompra ordenCompra;

    @Column(name = "warehouse_id", nullable = false)
    private UUID idAlmacen;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private EstadoRecepcionMercaderia estado = EstadoRecepcionMercaderia.BORRADOR;

    @Column(name = "received_by", length = 120)
    private String recibidoPor;

    @Column(name = "received_at")
    private OffsetDateTime recibidoEn;

    @Column(name = "notes", columnDefinition = "text")
    private String notas;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime creadoEn;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime actualizadoEn;
}