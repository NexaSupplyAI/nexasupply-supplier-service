package com.nexasupplyai.supplier.service.interfaces;

import com.nexasupplyai.supplier.dto.request.OrdenCompraRequest;
import com.nexasupplyai.supplier.dto.response.OrdenCompraResponse;
import java.util.List;
import java.util.UUID;

public interface OrdenCompraServiceInterface {

	List<OrdenCompraResponse> listar();

	List<OrdenCompraResponse> listarPorProveedor(UUID idProveedor);

	OrdenCompraResponse obtenerPorId(UUID id);

	OrdenCompraResponse crear(OrdenCompraRequest request);

	OrdenCompraResponse actualizar(UUID id, OrdenCompraRequest request);
	OrdenCompraResponse enviar(UUID id, String actor);
	OrdenCompraResponse aprobar(UUID id, String actor);
	OrdenCompraResponse cancelar(UUID id, String actor, String motivo);

	void eliminar(UUID id);
}
