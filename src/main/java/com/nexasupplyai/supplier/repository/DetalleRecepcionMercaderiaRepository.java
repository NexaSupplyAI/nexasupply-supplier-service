package com.nexasupplyai.supplier.repository;
import com.nexasupplyai.supplier.entity.DetalleRecepcionMercaderia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; import java.util.Optional; import java.util.UUID;

@Repository
public interface DetalleRecepcionMercaderiaRepository extends JpaRepository<DetalleRecepcionMercaderia, UUID> {
    List<DetalleRecepcionMercaderia> findAllByRecepcionMercaderiaId(UUID idRecepcion);
    Optional<DetalleRecepcionMercaderia> findByRecepcionMercaderiaIdAndDetalleOrdenCompraId(UUID idRecepcion, UUID idDetalleOrdenCompra);
}
