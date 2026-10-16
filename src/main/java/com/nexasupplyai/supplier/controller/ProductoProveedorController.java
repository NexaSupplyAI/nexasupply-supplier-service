package com.nexasupplyai.supplier.controller;

import com.nexasupplyai.supplier.dto.request.ProductoProveedorRequest;
import com.nexasupplyai.supplier.dto.response.ProductoProveedorResponse;
import com.nexasupplyai.supplier.service.interfaces.ProductoProveedorServiceInterface;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/productos-proveedor")
@RequiredArgsConstructor
public class ProductoProveedorController {

    private final ProductoProveedorServiceInterface servicio;

    @GetMapping
    public List<ProductoProveedorResponse> listar(
            @RequestParam UUID idProveedor) {
        return servicio.listarPorProveedor(idProveedor);
    }

    @GetMapping("/{id}")
    public ProductoProveedorResponse obtener(
            @PathVariable UUID id) {
        return servicio.obtenerPorId(id);
    }

    @PostMapping
    public ResponseEntity<ProductoProveedorResponse> crear(
            @Valid @RequestBody ProductoProveedorRequest r) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(servicio.crear(r));
    }

    @PutMapping("/{id}")
    public ProductoProveedorResponse actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody ProductoProveedorRequest r) {
        return servicio.actualizar(id, r);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable UUID id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}