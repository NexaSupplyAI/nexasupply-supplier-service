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

    @org.mapstruct.Mapping(target = "id", ignore = true)
    @org.mapstruct.Mapping(target = "ordenCompra", ignore = true)
    @org.mapstruct.Mapping(target = "estado", ignore = true)
    @org.mapstruct.Mapping(target = "recibidoPor", ignore = true)
    @org.mapstruct.Mapping(target = "recibidoEn", ignore = true)
    @org.mapstruct.Mapping(target = "creadoEn", ignore = true)
    @org.mapstruct.Mapping(target = "actualizadoEn", ignore = true)
    RecepcionMercaderia aEntidad(RecepcionMercaderiaRequest request, @Context OrdenCompra ordenCompra);

    @org.mapstruct.Mapping(target = "id", ignore = true)
    @org.mapstruct.Mapping(target = "ordenCompra", ignore = true)
    @org.mapstruct.Mapping(target = "estado", ignore = true)
    @org.mapstruct.Mapping(target = "recibidoPor", ignore = true)
    @org.mapstruct.Mapping(target = "recibidoEn", ignore = true)
    @org.mapstruct.Mapping(target = "creadoEn", ignore = true)
    @org.mapstruct.Mapping(target = "actualizadoEn", ignore = true)
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
