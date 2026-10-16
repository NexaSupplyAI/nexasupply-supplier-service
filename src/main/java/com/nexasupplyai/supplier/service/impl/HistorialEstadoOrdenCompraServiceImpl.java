package com.nexasupplyai.supplier.service.impl;

import com.nexasupplyai.supplier.dto.request.HistorialEstadoOrdenCompraRequest;
import com.nexasupplyai.supplier.dto.response.HistorialEstadoOrdenCompraResponse;
import com.nexasupplyai.supplier.entity.HistorialEstadoOrdenCompra;
import com.nexasupplyai.supplier.entity.OrdenCompra;
import com.nexasupplyai.supplier.mapper.HistorialEstadoOrdenCompraMapper;
import com.nexasupplyai.supplier.repository.HistorialEstadoOrdenCompraRepository;
import com.nexasupplyai.supplier.repository.OrdenCompraRepository;
import com.nexasupplyai.supplier.service.interfaces.HistorialEstadoOrdenCompraServiceInterface;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HistorialEstadoOrdenCompraServiceImpl implements HistorialEstadoOrdenCompraServiceInterface {

	private final HistorialEstadoOrdenCompraRepository repositorio;
	private final OrdenCompraRepository ordenes;
	private final HistorialEstadoOrdenCompraMapper mapper;

	public List<HistorialEstadoOrdenCompraResponse> listarPorOrden(UUID id) {
		return repositorio.findAllByOrdenCompraIdOrderByCambiadoEnDesc(id).stream()
				.map(mapper::aResponse)
				.toList();
	}

	public HistorialEstadoOrdenCompraResponse obtenerPorId(UUID id) {
		HistorialEstadoOrdenCompra entidad = repositorio.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Historial no encontrado: " + id));
		return mapper.aResponse(entidad);
	}

	@Transactional
	public HistorialEstadoOrdenCompraResponse crear(HistorialEstadoOrdenCompraRequest request) {
		OrdenCompra ordenCompra = ordenes.findById(request.idOrdenCompra())
				.orElseThrow(() -> new EntityNotFoundException(
						"Orden no encontrada: " + request.idOrdenCompra()));
		return mapper.aResponse(repositorio.save(mapper.aEntidad(request, ordenCompra)));
	}
}
