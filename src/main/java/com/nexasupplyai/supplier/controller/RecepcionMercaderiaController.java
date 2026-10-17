package com.nexasupplyai.supplier.controller;

import com.nexasupplyai.supplier.dto.request.RecepcionMercaderiaRequest;
import com.nexasupplyai.supplier.dto.response.RecepcionMercaderiaResponse;
import com.nexasupplyai.supplier.service.interfaces.RecepcionMercaderiaServiceInterface;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/v1/recepciones-mercaderia")
@RequiredArgsConstructor
public class RecepcionMercaderiaController {

    private final RecepcionMercaderiaServiceInterface servicio;

    @GetMapping
    public List<RecepcionMercaderiaResponse> listar(
            @RequestParam UUID idOrden) {
        return servicio.listarPorOrden(idOrden);
    }

    @GetMapping("/{id}")
    public RecepcionMercaderiaResponse obtener(
            @PathVariable UUID id) {
        return servicio.obtenerPorId(id);
    }

    @PostMapping
    public ResponseEntity<RecepcionMercaderiaResponse> crear(
            @Valid @RequestBody RecepcionMercaderiaRequest r) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(servicio.crear(r));
    }

    @PutMapping("/{id}")
    public RecepcionMercaderiaResponse actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody RecepcionMercaderiaRequest r) {
        return servicio.actualizar(id, r);
    }

    @PostMapping("/{id}/postear")
    public RecepcionMercaderiaResponse postear(@PathVariable UUID id, Authentication authentication) {
        return servicio.postear(id, authentication == null ? "local-demo-user" : authentication.getName());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable UUID id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
