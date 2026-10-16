package com.nexasupplyai.supplier.service.interfaces;

import com.nexasupplyai.supplier.dto.request.RecepcionMercaderiaRequest;
import com.nexasupplyai.supplier.dto.response.RecepcionMercaderiaResponse;
import java.util.List;
import java.util.UUID;

public interface RecepcionMercaderiaServiceInterface {

	List<RecepcionMercaderiaResponse> listarPorOrden(UUID idOrden);

	RecepcionMercaderiaResponse obtenerPorId(UUID id);

	RecepcionMercaderiaResponse crear(RecepcionMercaderiaRequest request);

	RecepcionMercaderiaResponse actualizar(UUID id, RecepcionMercaderiaRequest request);

	void eliminar(UUID id);
}
