package com.nexasupplyai.supplier.repository;
import com.nexasupplyai.supplier.entity.RecepcionMercaderia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List; import java.util.Optional; import java.util.UUID;
import jakarta.persistence.LockModeType;

@Repository
public interface RecepcionMercaderiaRepository extends JpaRepository<RecepcionMercaderia, UUID> {
    Optional<RecepcionMercaderia> findByNumeroRecepcion(String numeroRecepcion);
    List<RecepcionMercaderia> findAllByOrdenCompraId(UUID idOrdenCompra);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select recepcion from RecepcionMercaderia recepcion where recepcion.id = :id")
    Optional<RecepcionMercaderia> bloquearPorId(UUID id);
}
