package com.nexasupplyai.supplier.service.impl;

import com.nexasupplyai.supplier.dto.request.DetalleRecepcionMercaderiaRequest;
import com.nexasupplyai.supplier.dto.response.DetalleRecepcionMercaderiaResponse;
import com.nexasupplyai.supplier.entity.DetalleOrdenCompra;
import com.nexasupplyai.supplier.entity.DetalleRecepcionMercaderia;
import com.nexasupplyai.supplier.entity.RecepcionMercaderia;
import com.nexasupplyai.supplier.mapper.DetalleRecepcionMercaderiaMapper;
import com.nexasupplyai.supplier.repository.DetalleOrdenCompraRepository;
import com.nexasupplyai.supplier.repository.DetalleRecepcionMercaderiaRepository;
import com.nexasupplyai.supplier.repository.RecepcionMercaderiaRepository;
import com.nexasupplyai.supplier.service.interfaces.DetalleRecepcionMercaderiaServiceInterface;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetalleRecepcionMercaderiaServiceImpl implements DetalleRecepcionMercaderiaServiceInterface {

	private final DetalleRecepcionMercaderiaRepository repositorio;
	private final RecepcionMercaderiaRepository recepciones;
	private final DetalleOrdenCompraRepository detalles;
	private final DetalleRecepcionMercaderiaMapper mapper;

	public List<DetalleRecepcionMercaderiaResponse> listarPorRecepcion(UUID id) {
		return repositorio.findAllByRecepcionMercaderiaId(id).stream()
				.map(mapper::aResponse)
				.toList();
	}

	public DetalleRecepcionMercaderiaResponse obtenerPorId(UUID id) {
		return mapper.aResponse(buscar(id));
	}

	@Transactional
	public DetalleRecepcionMercaderiaResponse crear(DetalleRecepcionMercaderiaRequest request) {
		return mapper.aResponse(repositorio.save(mapper.aEntidad(
				request,
				recepcion(request.idRecepcionMercaderia()),
				detalle(request.idDetalleOrdenCompra()))));
	}

	@Transactional
	public void eliminar(UUID id) {
		repositorio.delete(buscar(id));
	}

	private DetalleRecepcionMercaderia buscar(UUID id) {
		return repositorio.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Detalle de recepción no encontrado: " + id));
	}

	private RecepcionMercaderia recepcion(UUID id) {
		return recepciones.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Recepción no encontrada: " + id));
	}

	private DetalleOrdenCompra detalle(UUID id) {
		return detalles.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Detalle de orden no encontrado: " + id));
	}
}
