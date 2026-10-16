package com.nexasupplyai.supplier.mapper;

import com.nexasupplyai.supplier.dto.request.RecepcionMercaderiaRequest;
import com.nexasupplyai.supplier.dto.response.RecepcionMercaderiaResponse;
import com.nexasupplyai.supplier.entity.OrdenCompra;
import com.nexasupplyai.supplier.entity.RecepcionMercaderia;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RecepcionMercaderiaMapper {

    RecepcionMercaderia aEntidad(RecepcionMercaderiaRequest request, @Context OrdenCompra ordenCompra);

    void actualizarEntidad(
            RecepcionMercaderiaRequest request,
            @Context OrdenCompra ordenCompra,
            @MappingTarget RecepcionMercaderia entidad);

    @org.mapstruct.Mapping(target = "idOrdenCompra", source = "ordenCompra.id")
    RecepcionMercaderiaResponse aResponse(RecepcionMercaderia entidad);

    @AfterMapping
    default void asignarOrden(
            @MappingTarget RecepcionMercaderia entidad,
            @Context OrdenCompra ordenCompra) {
        entidad.setOrdenCompra(ordenCompra);
    }
}
