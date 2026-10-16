package com.nexasupplyai.supplier.mapper;

import com.nexasupplyai.supplier.dto.request.DetalleRecepcionMercaderiaRequest;
import com.nexasupplyai.supplier.dto.response.DetalleRecepcionMercaderiaResponse;
import com.nexasupplyai.supplier.entity.DetalleOrdenCompra;
import com.nexasupplyai.supplier.entity.DetalleRecepcionMercaderia;
import com.nexasupplyai.supplier.entity.RecepcionMercaderia;
import org.mapstruct.AfterMapping; import org.mapstruct.Context; import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DetalleRecepcionMercaderiaMapper {
    DetalleRecepcionMercaderia aEntidad(DetalleRecepcionMercaderiaRequest request, @Context RecepcionMercaderia recepcion, @Context DetalleOrdenCompra detalleOrden);
    void actualizarEntidad(DetalleRecepcionMercaderiaRequest request, @Context RecepcionMercaderia recepcion, @Context DetalleOrdenCompra detalleOrden, @MappingTarget DetalleRecepcionMercaderia entidad);
    @org.mapstruct.Mapping(target = "idRecepcionMercaderia", source = "recepcionMercaderia.id")
    @org.mapstruct.Mapping(target = "idDetalleOrdenCompra", source = "detalleOrdenCompra.id")
    DetalleRecepcionMercaderiaResponse aResponse(DetalleRecepcionMercaderia entidad);
    @AfterMapping default void asignarRelaciones(@MappingTarget DetalleRecepcionMercaderia entidad, @Context RecepcionMercaderia recepcion, @Context DetalleOrdenCompra detalleOrden) { entidad.setRecepcionMercaderia(recepcion); entidad.setDetalleOrdenCompra(detalleOrden); }
}
