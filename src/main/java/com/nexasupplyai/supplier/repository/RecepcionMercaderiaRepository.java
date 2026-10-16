package com.nexasupplyai.supplier.repository;
import com.nexasupplyai.supplier.entity.RecepcionMercaderia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; import java.util.Optional; import java.util.UUID;

@Repository
public interface RecepcionMercaderiaRepository extends JpaRepository<RecepcionMercaderia, UUID> {
    Optional<RecepcionMercaderia> findByNumeroRecepcion(String numeroRecepcion);
    List<RecepcionMercaderia> findAllByOrdenCompraId(UUID idOrdenCompra);
}
