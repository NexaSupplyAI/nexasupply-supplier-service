package com.nexasupplyai.supplier.repository;
import com.nexasupplyai.supplier.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; import java.util.UUID;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, UUID> {
    Optional<Proveedor> findByCodigoProveedor(String codigoProveedor);
    Optional<Proveedor> findByNumeroFiscal(String numeroFiscal);
    Optional<Proveedor> findByEmailIgnoreCase(String email);
}
