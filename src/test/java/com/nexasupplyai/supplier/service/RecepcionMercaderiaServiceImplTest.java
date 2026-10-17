package com.nexasupplyai.supplier.service;

import com.nexasupplyai.supplier.client.InventoryServiceClient;
import com.nexasupplyai.supplier.entity.*;
import com.nexasupplyai.supplier.enums.*;
import com.nexasupplyai.supplier.mapper.RecepcionMercaderiaMapper;
import com.nexasupplyai.supplier.repository.*;
import com.nexasupplyai.supplier.service.impl.RecepcionMercaderiaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecepcionMercaderiaServiceImplTest {
    @Mock RecepcionMercaderiaRepository recepciones;
    @Mock OrdenCompraRepository ordenes;
    @Mock RecepcionMercaderiaMapper mapper;
    @Mock InventoryServiceClient inventario;
    @Mock DetalleRecepcionMercaderiaRepository detallesRecepcion;
    @Mock DetalleOrdenCompraRepository detallesOrden;

    @Test
    void postearRegistraEntradaYActualizaOrden() {
        UUID recepcionId = UUID.randomUUID();
        OrdenCompra orden = new OrdenCompra(); orden.setId(UUID.randomUUID()); orden.setEstado(EstadoOrdenCompra.APPROVED);
        RecepcionMercaderia recepcion = new RecepcionMercaderia(); recepcion.setId(recepcionId);
        recepcion.setOrdenCompra(orden); recepcion.setIdAlmacen(UUID.randomUUID());
        recepcion.setNumeroRecepcion("REC-1"); recepcion.setEstado(EstadoRecepcionMercaderia.DRAFT);
        DetalleOrdenCompra detalleOrden = new DetalleOrdenCompra(); detalleOrden.setId(UUID.randomUUID());
        detalleOrden.setOrdenCompra(orden); detalleOrden.setIdProducto(UUID.randomUUID());
        detalleOrden.setSkuInstantanea("SKU-1"); detalleOrden.setCantidadPedida(new BigDecimal("5"));
        detalleOrden.setCantidadRecibida(BigDecimal.ZERO);
        DetalleRecepcionMercaderia linea = new DetalleRecepcionMercaderia(); linea.setId(UUID.randomUUID());
        linea.setRecepcionMercaderia(recepcion); linea.setDetalleOrdenCompra(detalleOrden);
        linea.setIdProducto(detalleOrden.getIdProducto()); linea.setCantidadRecibida(new BigDecimal("5"));
        when(recepciones.bloquearPorId(recepcionId)).thenReturn(Optional.of(recepcion));
        when(detallesRecepcion.findAllByRecepcionMercaderiaId(recepcionId)).thenReturn(List.of(linea));
        when(detallesOrden.findAllByOrdenCompraId(orden.getId())).thenReturn(List.of(detalleOrden));
        when(recepciones.save(any())).thenAnswer(i -> i.getArgument(0));
        var servicio = new RecepcionMercaderiaServiceImpl(recepciones, ordenes, mapper, inventario,
                detallesRecepcion, detallesOrden);

        servicio.postear(recepcionId, "operador");

        assertEquals(EstadoRecepcionMercaderia.POSTED, recepcion.getEstado());
        assertEquals(new BigDecimal("5"), detalleOrden.getCantidadRecibida());
        assertEquals(EstadoOrdenCompra.RECEIVED, orden.getEstado());
        verify(inventario).registrarEntrada(any());
    }
}
