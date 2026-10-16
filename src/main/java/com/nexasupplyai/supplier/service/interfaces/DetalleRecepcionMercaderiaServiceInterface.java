package com.nexasupplyai.supplier.service.interfaces;

import com.nexasupplyai.supplier.dto.request.DetalleRecepcionMercaderiaRequest;
import com.nexasupplyai.supplier.dto.response.DetalleRecepcionMercaderiaResponse;
import java.util.List;
import java.util.UUID;

public interface DetalleRecepcionMercaderiaServiceInterface {

	List<DetalleRecepcionMercaderiaResponse> listarPorRecepcion(UUID idRecepcion);

	DetalleRecepcionMercaderiaResponse obtenerPorId(UUID id);

	DetalleRecepcionMercaderiaResponse crear(DetalleRecepcionMercaderiaRequest request);

	void eliminar(UUID id);
}
