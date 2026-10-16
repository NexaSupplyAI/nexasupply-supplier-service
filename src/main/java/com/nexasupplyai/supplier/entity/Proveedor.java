package com.nexasupplyai.supplier.entity;

import com.nexasupplyai.supplier.enums.EstadoProveedor;
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
@Table(name = "suppliers", schema = "supplier")
@Getter
@Setter
@NoArgsConstructor
public class Proveedor {

    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false)
    private UUID id;

    @Column(name = "supplier_code", nullable = false, unique = true, length = 40)
    private String codigoProveedor;

    @Column(name = "tax_id", unique = true, length = 30)
    private String numeroFiscal;

    @Column(name = "business_name", nullable = false, length = 180)
    private String razonSocial;

    @Column(name = "trade_name", length = 180)
    private String nombreComercial;

    @Column(name = "email", length = 180)
    private String email;

    @Column(name = "phone", length = 50)
    private String telefono;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private EstadoProveedor estado = EstadoProveedor.ACTIVE;

    @Column(name = "rating", precision = 3, scale = 2)
    private BigDecimal calificacion;

    @Column(name = "default_lead_time_days", nullable = false)
    private Integer diasEntregaPredeterminados = 0;

    @Column(name = "address_line", length = 220)
    private String direccion;

    @Column(name = "district", length = 120)
    private String distrito;

    @Column(name = "city", length = 120)
    private String ciudad;

    @Column(name = "region", length = 120)
    private String region;

    @Column(name = "country_code", nullable = false, length = 2)
    private String codigoPais = "PE";

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
