package com.nexasupplyai.supplier.controller;

import com.nexasupplyai.supplier.dto.request.DetalleRecepcionMercaderiaRequest;
import com.nexasupplyai.supplier.dto.response.DetalleRecepcionMercaderiaResponse;
import com.nexasupplyai.supplier.service.interfaces.DetalleRecepcionMercaderiaServiceInterface;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/detalles-recepcion-mercaderia")
@RequiredArgsConstructor
public class DetalleRecepcionMercaderiaController {

    private final DetalleRecepcionMercaderiaServiceInterface servicio;

    @GetMapping
    public List<DetalleRecepcionMercaderiaResponse> listar(
            @RequestParam UUID idRecepcion) {
        return servicio.listarPorRecepcion(idRecepcion);
    }

    @GetMapping("/{id}")
    public DetalleRecepcionMercaderiaResponse obtener(
            @PathVariable UUID id) {
        return servicio.obtenerPorId(id);
    }

    @PostMapping
    public ResponseEntity<DetalleRecepcionMercaderiaResponse> crear(
            @Valid @RequestBody DetalleRecepcionMercaderiaRequest r) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(servicio.crear(r));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable UUID id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}