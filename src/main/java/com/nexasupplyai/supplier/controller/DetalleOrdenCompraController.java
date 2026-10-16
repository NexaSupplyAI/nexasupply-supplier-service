package com.nexasupplyai.supplier.controller;

import com.nexasupplyai.supplier.dto.request.DetalleOrdenCompraRequest;
import com.nexasupplyai.supplier.dto.response.DetalleOrdenCompraResponse;
import com.nexasupplyai.supplier.service.interfaces.DetalleOrdenCompraServiceInterface;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/detalles-orden-compra")
@RequiredArgsConstructor
public class DetalleOrdenCompraController {

    private final DetalleOrdenCompraServiceInterface servicio;

    @GetMapping
    public List<DetalleOrdenCompraResponse> listar(
            @RequestParam UUID idOrden) {
        return servicio.listarPorOrden(idOrden);
    }

    @GetMapping("/{id}")
    public DetalleOrdenCompraResponse obtener(
            @PathVariable UUID id) {
        return servicio.obtenerPorId(id);
    }

    @PostMapping
    public ResponseEntity<DetalleOrdenCompraResponse> crear(
            @Valid @RequestBody DetalleOrdenCompraRequest r) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(servicio.crear(r));
    }

    @PutMapping("/{id}")
    public DetalleOrdenCompraResponse actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody DetalleOrdenCompraRequest r) {
        return servicio.actualizar(id, r);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable UUID id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}