package com.nexasupplyai.supplier.entity;

import com.nexasupplyai.supplier.enums.EstadoOrdenCompra;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "purchase_order_status_history", schema = "supplier")
@Getter
@Setter
@NoArgsConstructor
public class HistorialEstadoOrdenCompra {

    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "purchase_order_id", nullable = false)
    private OrdenCompra ordenCompra;

    @Enumerated(EnumType.STRING)
    @Column(name = "previous_status", length = 30)
    private EstadoOrdenCompra estadoAnterior;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status", nullable = false, length = 30)
    private EstadoOrdenCompra nuevoEstado;

    @Column(name = "reason", length = 300)
    private String motivo;

    @Column(name = "changed_by", length = 120)
    private String cambiadoPor;

    @CreationTimestamp
    @Column(name = "changed_at", nullable = false)
    private OffsetDateTime cambiadoEn;
}