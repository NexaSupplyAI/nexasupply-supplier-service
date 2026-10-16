package com.nexasupplyai.supplier.repository;
import com.nexasupplyai.supplier.entity.HistorialEstadoOrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; import java.util.UUID;

@Repository
public interface HistorialEstadoOrdenCompraRepository extends JpaRepository<HistorialEstadoOrdenCompra, UUID> {
    List<HistorialEstadoOrdenCompra> findAllByOrdenCompraIdOrderByCambiadoEnDesc(UUID idOrdenCompra);
}
