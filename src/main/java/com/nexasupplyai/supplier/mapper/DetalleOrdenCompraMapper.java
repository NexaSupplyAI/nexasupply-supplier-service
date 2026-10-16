package com.nexasupplyai.supplier.mapper;

import com.nexasupplyai.supplier.dto.request.DetalleOrdenCompraRequest;
import com.nexasupplyai.supplier.dto.response.DetalleOrdenCompraResponse;
import com.nexasupplyai.supplier.entity.DetalleOrdenCompra;
import com.nexasupplyai.supplier.entity.OrdenCompra;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DetalleOrdenCompraMapper {

    DetalleOrdenCompra aEntidad(DetalleOrdenCompraRequest request, @Context OrdenCompra ordenCompra);

    void actualizarEntidad(
            DetalleOrdenCompraRequest request,
            @Context OrdenCompra ordenCompra,
            @MappingTarget DetalleOrdenCompra entidad);

    @org.mapstruct.Mapping(target = "idOrdenCompra", source = "ordenCompra.id")
    DetalleOrdenCompraResponse aResponse(DetalleOrdenCompra entidad);

    @AfterMapping
    default void asignarOrden(
            @MappingTarget DetalleOrdenCompra entidad,
            @Context OrdenCompra ordenCompra) {
        entidad.setOrdenCompra(ordenCompra);
    }
}
