package com.nexasupplyai.supplier.controller;

import com.nexasupplyai.supplier.dto.request.ProveedorRequest;
import com.nexasupplyai.supplier.dto.response.ProveedorResponse;
import com.nexasupplyai.supplier.service.interfaces.ProveedorServiceInterface;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/proveedores")
@RequiredArgsConstructor
public class ProveedorController {

    private final ProveedorServiceInterface servicio;

    @GetMapping
    public List<ProveedorResponse> listar() {
        return servicio.listar();
    }

    @GetMapping("/{id}")
    public ProveedorResponse obtener(
            @PathVariable UUID id) {
        return servicio.obtenerPorId(id);
    }

    @PostMapping
    public ResponseEntity<ProveedorResponse> crear(
            @Valid @RequestBody ProveedorRequest r) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(servicio.crear(r));
    }

    @PutMapping("/{id}")
    public ProveedorResponse actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody ProveedorRequest r) {
        return servicio.actualizar(id, r);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable UUID id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}