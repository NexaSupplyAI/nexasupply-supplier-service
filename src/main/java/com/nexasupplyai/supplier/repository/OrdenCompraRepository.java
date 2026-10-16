package com.nexasupplyai.supplier.repository;
import com.nexasupplyai.supplier.entity.OrdenCompra;
import com.nexasupplyai.supplier.enums.EstadoOrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; import java.util.Optional; import java.util.UUID;

@Repository
public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, UUID> {
    Optional<OrdenCompra> findByNumeroOrdenCompra(String numeroOrdenCompra);
    List<OrdenCompra> findAllByProveedorId(UUID idProveedor);
    List<OrdenCompra> findAllByEstadoOrderByCreadoEnDesc(EstadoOrdenCompra estado);
}
