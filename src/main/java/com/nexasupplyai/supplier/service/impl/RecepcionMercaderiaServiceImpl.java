package com.nexasupplyai.supplier.service.impl;

import com.nexasupplyai.supplier.dto.request.RecepcionMercaderiaRequest;
import com.nexasupplyai.supplier.dto.response.RecepcionMercaderiaResponse;
import com.nexasupplyai.supplier.entity.OrdenCompra;
import com.nexasupplyai.supplier.entity.RecepcionMercaderia;
import com.nexasupplyai.supplier.mapper.RecepcionMercaderiaMapper;
import com.nexasupplyai.supplier.repository.OrdenCompraRepository;
import com.nexasupplyai.supplier.repository.RecepcionMercaderiaRepository;
import com.nexasupplyai.supplier.service.interfaces.RecepcionMercaderiaServiceInterface;
import com.nexasupplyai.supplier.exception.RecepcionMercaderiaNoEncontradaException;
import com.nexasupplyai.supplier.exception.OrdenCompraNoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecepcionMercaderiaServiceImpl implements RecepcionMercaderiaServiceInterface {

	private final RecepcionMercaderiaRepository repositorio;
	private final OrdenCompraRepository ordenes;
	private final RecepcionMercaderiaMapper mapper;

	public List<RecepcionMercaderiaResponse> listarPorOrden(UUID id) {
		return repositorio.findAllByOrdenCompraId(id).stream()
				.map(mapper::aResponse)
				.toList();
	}

	public RecepcionMercaderiaResponse obtenerPorId(UUID id) {
		return mapper.aResponse(buscar(id));
	}

	@Transactional
	public RecepcionMercaderiaResponse crear(RecepcionMercaderiaRequest request) {
		return mapper.aResponse(repositorio.save(mapper.aEntidad(request, orden(request.idOrdenCompra()))));
	}

	@Transactional
	public RecepcionMercaderiaResponse actualizar(UUID id, RecepcionMercaderiaRequest request) {
		RecepcionMercaderia entidad = buscar(id);
		mapper.actualizarEntidad(request, orden(request.idOrdenCompra()), entidad);
		return mapper.aResponse(repositorio.save(entidad));
	}

	@Transactional
	public void eliminar(UUID id) {
		repositorio.delete(buscar(id));
	}

	private RecepcionMercaderia buscar(UUID id) {
		return repositorio.findById(id)
                .orElseThrow(() -> new RecepcionMercaderiaNoEncontradaException(id));
	}

	private OrdenCompra orden(UUID id) {
		return ordenes.findById(id)
                .orElseThrow(() -> new OrdenCompraNoEncontradaException(id));
	}
}
