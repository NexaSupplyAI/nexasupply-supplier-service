package com.nexasupplyai.supplier.service.interfaces;

import com.nexasupplyai.supplier.dto.response.HistorialEstadoOrdenCompraResponse;
import java.util.List;
import java.util.UUID;

public interface HistorialEstadoOrdenCompraServiceInterface {

	List<HistorialEstadoOrdenCompraResponse> listarPorOrden(UUID idOrden);

	HistorialEstadoOrdenCompraResponse obtenerPorId(UUID id);

}
