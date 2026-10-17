package com.nexasupplyai.supplier.mapper;

import com.nexasupplyai.supplier.dto.request.OrdenCompraRequest;
import com.nexasupplyai.supplier.dto.response.OrdenCompraResponse;
import com.nexasupplyai.supplier.entity.OrdenCompra;
import com.nexasupplyai.supplier.entity.Proveedor;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrdenCompraMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "proveedor", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "aprobadoPor", ignore = true)
    @Mapping(target = "aprobadoEn", ignore = true)
    @Mapping(target = "creadoEn", ignore = true)
    @Mapping(target = "actualizadoEn", ignore = true)
    @Mapping(target = "version", ignore = true)
    OrdenCompra aEntidad(OrdenCompraRequest request, @Context Proveedor proveedor);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "proveedor", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "aprobadoPor", ignore = true)
    @Mapping(target = "aprobadoEn", ignore = true)
    @Mapping(target = "creadoEn", ignore = true)
    @Mapping(target = "actualizadoEn", ignore = true)
    @Mapping(target = "version", ignore = true)
    void actualizarEntidad(
            OrdenCompraRequest request,
            @Context Proveedor proveedor,
            @MappingTarget OrdenCompra entidad);

    @org.mapstruct.Mapping(target = "idProveedor", source = "proveedor.id")
    OrdenCompraResponse aResponse(OrdenCompra entidad);

    @AfterMapping
    default void asignarProveedor(
            @MappingTarget OrdenCompra entidad,
            @Context Proveedor proveedor) {
        entidad.setProveedor(proveedor);
    }
}
