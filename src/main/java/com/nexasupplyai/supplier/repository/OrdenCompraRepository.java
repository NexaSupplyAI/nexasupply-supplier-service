package com.nexasupplyai.supplier.repository;
import com.nexasupplyai.supplier.entity.OrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import jakarta.persistence.LockModeType;

import java.util.List; import java.util.Optional; import java.util.UUID;

@Repository
public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, UUID> {
    Optional<OrdenCompra> findByNumeroOrdenCompra(String numeroOrdenCompra);
    List<OrdenCompra> findAllByProveedorId(UUID idProveedor);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select o from OrdenCompra o where o.id = :id")
    Optional<OrdenCompra> bloquearPorId(UUID id);
}
