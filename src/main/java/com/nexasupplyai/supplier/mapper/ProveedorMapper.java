package com.nexasupplyai.supplier.mapper;

import com.nexasupplyai.supplier.dto.request.ProveedorRequest;
import com.nexasupplyai.supplier.dto.response.ProveedorResponse;
import com.nexasupplyai.supplier.entity.Proveedor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProveedorMapper {

    @Mapping(target = "estado", source = "estado", defaultValue = "ACTIVE")
    Proveedor aEntidad(ProveedorRequest request);

    void actualizarEntidad(ProveedorRequest request, @MappingTarget Proveedor proveedor);

    ProveedorResponse aResponse(Proveedor proveedor);
}
