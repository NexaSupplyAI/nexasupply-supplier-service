package com.nexasupplyai.supplier.mapper;

import com.nexasupplyai.supplier.dto.request.DetalleRecepcionMercaderiaRequest;
import com.nexasupplyai.supplier.dto.response.DetalleRecepcionMercaderiaResponse;
import com.nexasupplyai.supplier.entity.DetalleOrdenCompra;
import com.nexasupplyai.supplier.entity.DetalleRecepcionMercaderia;
import com.nexasupplyai.supplier.entity.RecepcionMercaderia;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DetalleRecepcionMercaderiaMapper {

    @org.mapstruct.Mapping(target = "id", ignore = true)
    @org.mapstruct.Mapping(target = "recepcionMercaderia", ignore = true)
    @org.mapstruct.Mapping(target = "detalleOrdenCompra", ignore = true)
    @org.mapstruct.Mapping(target = "creadoEn", ignore = true)
    DetalleRecepcionMercaderia aEntidad(
            DetalleRecepcionMercaderiaRequest request,
            @Context RecepcionMercaderia recepcion,
            @Context DetalleOrdenCompra detalleOrden);

    @org.mapstruct.Mapping(target = "idRecepcionMercaderia", source = "recepcionMercaderia.id")
    @org.mapstruct.Mapping(target = "idDetalleOrdenCompra", source = "detalleOrdenCompra.id")
    DetalleRecepcionMercaderiaResponse aResponse(DetalleRecepcionMercaderia entidad);

    @AfterMapping
    default void asignarRelaciones(
            @MappingTarget DetalleRecepcionMercaderia entidad,
            @Context RecepcionMercaderia recepcion,
            @Context DetalleOrdenCompra detalleOrden) {
        entidad.setRecepcionMercaderia(recepcion);
        entidad.setDetalleOrdenCompra(detalleOrden);
    }
}
