package com.nexasupplyai.supplier.repository;
import com.nexasupplyai.supplier.entity.DetalleOrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; import java.util.Optional; import java.util.UUID;

@Repository
public interface DetalleOrdenCompraRepository extends JpaRepository<DetalleOrdenCompra, UUID> {
    List<DetalleOrdenCompra> findAllByOrdenCompraId(UUID idOrdenCompra);
    Optional<DetalleOrdenCompra> findByOrdenCompraIdAndIdProducto(UUID idOrdenCompra, UUID idProducto);
}
