package com.nexasupplyai.supplier.mapper;

import com.nexasupplyai.supplier.dto.request.ProveedorRequest;
import com.nexasupplyai.supplier.dto.response.ProveedorResponse;
import com.nexasupplyai.supplier.entity.Proveedor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProveedorMapper {
    Proveedor aEntidad(ProveedorRequest request);
    void actualizarEntidad(ProveedorRequest request, @MappingTarget Proveedor proveedor);
    ProveedorResponse aResponse(Proveedor proveedor);
}
