package com.nexasupplyai.supplier.controller;

import com.nexasupplyai.supplier.dto.request.OrdenCompraRequest;
import com.nexasupplyai.supplier.dto.response.OrdenCompraResponse;
import com.nexasupplyai.supplier.service.interfaces.OrdenCompraServiceInterface;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/v1/ordenes-compra")
@RequiredArgsConstructor
public class OrdenCompraController {

    private final OrdenCompraServiceInterface servicio;

    @GetMapping
    public List<OrdenCompraResponse> listar(
            @RequestParam(required = false) UUID idProveedor) {
        return idProveedor == null ? servicio.listar() : servicio.listarPorProveedor(idProveedor);
    }

    @GetMapping("/{id}")
    public OrdenCompraResponse obtener(
            @PathVariable UUID id) {
        return servicio.obtenerPorId(id);
    }

    @PostMapping
    public ResponseEntity<OrdenCompraResponse> crear(
            @Valid @RequestBody OrdenCompraRequest r) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(servicio.crear(r));
    }

    @PutMapping("/{id}")
    public OrdenCompraResponse actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody OrdenCompraRequest r) {
        return servicio.actualizar(id, r);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable UUID id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/enviar")
    public OrdenCompraResponse enviar(@PathVariable UUID id, Authentication authentication) {
        return servicio.enviar(id, actor(authentication));
    }

    @PostMapping("/{id}/aprobar")
    public OrdenCompraResponse aprobar(@PathVariable UUID id, Authentication authentication) {
        return servicio.aprobar(id, actor(authentication));
    }

    @PostMapping("/{id}/cancelar")
    public OrdenCompraResponse cancelar(@PathVariable UUID id, @RequestParam(required = false) String motivo,
                                         Authentication authentication) {
        return servicio.cancelar(id, actor(authentication), motivo);
    }

    private String actor(Authentication authentication) {
        return authentication == null ? "local-demo-user" : authentication.getName();
    }
}
