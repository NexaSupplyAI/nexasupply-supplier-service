package com.nexasupplyai.supplier.service.interfaces;

import com.nexasupplyai.supplier.dto.request.ProveedorRequest;
import com.nexasupplyai.supplier.dto.response.ProveedorResponse;
import java.util.List;
import java.util.UUID;

public interface ProveedorServiceInterface {

	List<ProveedorResponse> listar();

	ProveedorResponse obtenerPorId(UUID id);

	ProveedorResponse crear(ProveedorRequest request);

	ProveedorResponse actualizar(UUID id, ProveedorRequest request);

	void eliminar(UUID id);
}
