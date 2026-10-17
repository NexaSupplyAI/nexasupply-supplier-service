package com.nexasupplyai.supplier.client;

import com.nexasupplyai.supplier.dto.external.AlmacenInventarioResponse;
import com.nexasupplyai.supplier.dto.external.ProductoInventarioResponse;
import com.nexasupplyai.supplier.dto.external.EntradaInventarioRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;
import java.util.Map;

@Component
public class InventoryServiceClient {

    private final RestClient clienteHttp;

    public InventoryServiceClient(
            RestClient.Builder constructorCliente,
            @Value("${clients.inventory-service.base-url:http://localhost:8081}") String urlBaseInventoryService
    ) {
        this.clienteHttp = constructorCliente.baseUrl(urlBaseInventoryService).build();
    }

    public ProductoInventarioResponse obtenerProducto(UUID idProducto) {
        return clienteHttp.get()
                .uri("/api/v1/productos/{id}", idProducto)
                .retrieve()
                .body(ProductoInventarioResponse.class);
    }

    public AlmacenInventarioResponse obtenerAlmacen(UUID idAlmacen) {
        return clienteHttp.get()
                .uri("/api/v1/almacenes/{id}", idAlmacen)
                .retrieve()
                .body(AlmacenInventarioResponse.class);
    }

    public Map<String, Object> registrarEntrada(EntradaInventarioRequest request) {
        return clienteHttp.post()
                .uri("/api/v1/operaciones-inventario/entradas")
                .body(request)
                .retrieve()
                .body(new org.springframework.core.ParameterizedTypeReference<>() {});
    }
}
