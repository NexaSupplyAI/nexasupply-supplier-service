package com.nexasupplyai.supplier.service.interfaces;

import com.nexasupplyai.supplier.dto.request.DetalleOrdenCompraRequest;
import com.nexasupplyai.supplier.dto.response.DetalleOrdenCompraResponse;
import java.util.List;
import java.util.UUID;

public interface DetalleOrdenCompraServiceInterface {

	List<DetalleOrdenCompraResponse> listarPorOrden(UUID idOrden);

	DetalleOrdenCompraResponse obtenerPorId(UUID id);

	DetalleOrdenCompraResponse crear(DetalleOrdenCompraRequest request);

	DetalleOrdenCompraResponse actualizar(UUID id, DetalleOrdenCompraRequest request);

	void eliminar(UUID id);
}
