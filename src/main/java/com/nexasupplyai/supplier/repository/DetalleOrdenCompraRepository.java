package com.nexasupplyai.supplier.repository;
import com.nexasupplyai.supplier.entity.DetalleOrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import jakarta.persistence.LockModeType;

import java.util.List; import java.util.Optional; import java.util.UUID;

@Repository
public interface DetalleOrdenCompraRepository extends JpaRepository<DetalleOrdenCompra, UUID> {
    List<DetalleOrdenCompra> findAllByOrdenCompraId(UUID idOrdenCompra);
    Optional<DetalleOrdenCompra> findByOrdenCompraIdAndIdProducto(UUID idOrdenCompra, UUID idProducto);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select d from DetalleOrdenCompra d where d.ordenCompra.id = :idOrdenCompra order by d.id")
    List<DetalleOrdenCompra> bloquearTodasPorOrden(UUID idOrdenCompra);
}
