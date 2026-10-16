package com.nexasupplyai.supplier.mapper;

import com.nexasupplyai.supplier.dto.request.OrdenCompraRequest;
import com.nexasupplyai.supplier.dto.response.OrdenCompraResponse;
import com.nexasupplyai.supplier.entity.OrdenCompra;
import com.nexasupplyai.supplier.entity.Proveedor;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrdenCompraMapper {

    OrdenCompra aEntidad(OrdenCompraRequest request, @Context Proveedor proveedor);

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
