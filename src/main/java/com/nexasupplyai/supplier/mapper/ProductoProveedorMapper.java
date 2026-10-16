package com.nexasupplyai.supplier.mapper;

import com.nexasupplyai.supplier.dto.request.ProductoProveedorRequest;
import com.nexasupplyai.supplier.dto.response.ProductoProveedorResponse;
import com.nexasupplyai.supplier.entity.ProductoProveedor;
import com.nexasupplyai.supplier.entity.Proveedor;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductoProveedorMapper {

    ProductoProveedor aEntidad(ProductoProveedorRequest request, @Context Proveedor proveedor);

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
