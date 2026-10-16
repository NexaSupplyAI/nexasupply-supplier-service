package com.nexasupplyai.supplier.repository;
import com.nexasupplyai.supplier.entity.ProductoProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; import java.util.Optional; import java.util.UUID;

@Repository
public interface ProductoProveedorRepository extends JpaRepository<ProductoProveedor, UUID> {
    List<ProductoProveedor> findAllByProveedorId(UUID idProveedor);
    List<ProductoProveedor> findAllByIdProductoAndActivoTrue(UUID idProducto);
    Optional<ProductoProveedor> findByProveedorIdAndIdProducto(UUID idProveedor, UUID idProducto);
}
