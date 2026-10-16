package com.nexasupplyai.supplier.service.impl;

import com.nexasupplyai.supplier.dto.request.ProductoProveedorRequest;
import com.nexasupplyai.supplier.dto.response.ProductoProveedorResponse;
import com.nexasupplyai.supplier.entity.ProductoProveedor;
import com.nexasupplyai.supplier.entity.Proveedor;
import com.nexasupplyai.supplier.mapper.ProductoProveedorMapper;
import com.nexasupplyai.supplier.repository.ProductoProveedorRepository;
import com.nexasupplyai.supplier.repository.ProveedorRepository;
import com.nexasupplyai.supplier.service.interfaces.ProductoProveedorServiceInterface;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductoProveedorServiceImpl implements ProductoProveedorServiceInterface {

    private final ProductoProveedorRepository repositorio;
    private final ProveedorRepository proveedores;
    private final ProductoProveedorMapper mapper;

    public List<ProductoProveedorResponse> listarPorProveedor(UUID id) {
        return repositorio.findAllByProveedorId(id).stream()
                .map(mapper::aResponse)
                .toList();
    }

    public ProductoProveedorResponse obtenerPorId(UUID id) {
        return mapper.aResponse(buscar(id));
    }

    @Transactional
    public ProductoProveedorResponse crear(ProductoProveedorRequest request) {
        Proveedor proveedor = proveedor(request.idProveedor());
        return mapper.aResponse(repositorio.save(mapper.aEntidad(request, proveedor)));
    }

    @Transactional
    public ProductoProveedorResponse actualizar(UUID id, ProductoProveedorRequest request) {
        ProductoProveedor entidad = buscar(id);
        mapper.actualizarEntidad(request, proveedor(request.idProveedor()), entidad);
        return mapper.aResponse(repositorio.save(entidad));
    }

    @Transactional
    public void eliminar(UUID id) {
        repositorio.delete(buscar(id));
    }

    private ProductoProveedor buscar(UUID id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Producto de proveedor no encontrado: " + id));
    }

    private Proveedor proveedor(UUID id) {
        return proveedores.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado: " + id));
    }
}
