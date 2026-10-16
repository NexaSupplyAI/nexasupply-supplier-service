package com.nexasupplyai.supplier.service.interfaces;

import com.nexasupplyai.supplier.dto.request.ProductoProveedorRequest;
import com.nexasupplyai.supplier.dto.response.ProductoProveedorResponse;
import java.util.List;
import java.util.UUID;

public interface ProductoProveedorServiceInterface {

	List<ProductoProveedorResponse> listarPorProveedor(UUID idProveedor);

	ProductoProveedorResponse obtenerPorId(UUID id);

	ProductoProveedorResponse crear(ProductoProveedorRequest request);

	ProductoProveedorResponse actualizar(UUID id, ProductoProveedorRequest request);

	void eliminar(UUID id);
}
