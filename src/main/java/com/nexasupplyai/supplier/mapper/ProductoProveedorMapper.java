package com.nexasupplyai.supplier.mapper;

import com.nexasupplyai.supplier.dto.request.ProductoProveedorRequest;
import com.nexasupplyai.supplier.dto.response.ProductoProveedorResponse;
import com.nexasupplyai.supplier.entity.ProductoProveedor;
import com.nexasupplyai.supplier.entity.Proveedor;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductoProveedorMapper {

    @Mapping(target = "preferido", source = "request.preferido", defaultValue = "false")
    @Mapping(target = "activo", source = "request.activo", defaultValue = "true")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "proveedor", ignore = true)
    @Mapping(target = "creadoEn", ignore = true)
    @Mapping(target = "actualizadoEn", ignore = true)
    ProductoProveedor aEntidad(ProductoProveedorRequest request, @Context Proveedor proveedor);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "proveedor", ignore = true)
    @Mapping(target = "creadoEn", ignore = true)
    @Mapping(target = "actualizadoEn", ignore = true)
    void actualizarEntidad(
            ProductoProveedorRequest request,
            @Context Proveedor proveedor,
            @MappingTarget ProductoProveedor entidad);

    @org.mapstruct.Mapping(target = "idProveedor", source = "proveedor.id")
    ProductoProveedorResponse aResponse(ProductoProveedor entidad);

    @AfterMapping
    default void asignarProveedor(
            @MappingTarget ProductoProveedor entidad,
            @Context Proveedor proveedor) {
        entidad.setProveedor(proveedor);
    }
}
