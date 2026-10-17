package com.nexasupplyai.supplier.mapper;

import com.nexasupplyai.supplier.dto.response.HistorialEstadoOrdenCompraResponse;
import com.nexasupplyai.supplier.entity.HistorialEstadoOrdenCompra;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HistorialEstadoOrdenCompraMapper {

    @org.mapstruct.Mapping(target = "idOrdenCompra", source = "ordenCompra.id")
    HistorialEstadoOrdenCompraResponse aResponse(HistorialEstadoOrdenCompra entidad);
}
