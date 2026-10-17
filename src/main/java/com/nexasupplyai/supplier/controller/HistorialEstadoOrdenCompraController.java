package com.nexasupplyai.supplier.controller;

import com.nexasupplyai.supplier.dto.response.HistorialEstadoOrdenCompraResponse;
import com.nexasupplyai.supplier.service.interfaces.HistorialEstadoOrdenCompraServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/historial-ordenes-compra")
@RequiredArgsConstructor
public class HistorialEstadoOrdenCompraController {

    private final HistorialEstadoOrdenCompraServiceInterface servicio;

    @GetMapping
    public List<HistorialEstadoOrdenCompraResponse> listar(
            @RequestParam UUID idOrden) {
        return servicio.listarPorOrden(idOrden);
    }

    @GetMapping("/{id}")
    public HistorialEstadoOrdenCompraResponse obtener(
            @PathVariable UUID id) {
        return servicio.obtenerPorId(id);
    }

}
