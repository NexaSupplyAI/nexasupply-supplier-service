package com.nexasupplyai.supplier.mapper;

import com.nexasupplyai.supplier.dto.request.HistorialEstadoOrdenCompraRequest;
import com.nexasupplyai.supplier.dto.response.HistorialEstadoOrdenCompraResponse;
import com.nexasupplyai.supplier.entity.HistorialEstadoOrdenCompra;
import com.nexasupplyai.supplier.entity.OrdenCompra;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface HistorialEstadoOrdenCompraMapper {

    HistorialEstadoOrdenCompra aEntidad(HistorialEstadoOrdenCompraRequest request, @Context OrdenCompra ordenCompra);

    void actualizarEntidad(
            HistorialEstadoOrdenCompraRequest request,
            @Context OrdenCompra ordenCompra,
            @MappingTarget HistorialEstadoOrdenCompra entidad);

    @org.mapstruct.Mapping(target = "idOrdenCompra", source = "ordenCompra.id")
    HistorialEstadoOrdenCompraResponse aResponse(HistorialEstadoOrdenCompra entidad);

    @AfterMapping
    default void asignarOrden(
            @MappingTarget HistorialEstadoOrdenCompra entidad,
            @Context OrdenCompra ordenCompra) {
        entidad.setOrdenCompra(ordenCompra);
    }
}
