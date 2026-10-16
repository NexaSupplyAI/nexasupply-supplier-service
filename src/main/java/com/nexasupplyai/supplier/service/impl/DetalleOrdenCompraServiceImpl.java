package com.nexasupplyai.supplier.service.impl;

import com.nexasupplyai.supplier.dto.request.DetalleOrdenCompraRequest;
import com.nexasupplyai.supplier.dto.response.DetalleOrdenCompraResponse;
import com.nexasupplyai.supplier.entity.DetalleOrdenCompra;
import com.nexasupplyai.supplier.entity.OrdenCompra;
import com.nexasupplyai.supplier.mapper.DetalleOrdenCompraMapper;
import com.nexasupplyai.supplier.repository.DetalleOrdenCompraRepository;
import com.nexasupplyai.supplier.repository.OrdenCompraRepository;
import com.nexasupplyai.supplier.service.interfaces.DetalleOrdenCompraServiceInterface;
import com.nexasupplyai.supplier.exception.DetalleOrdenCompraNoEncontradoException;
import com.nexasupplyai.supplier.exception.OrdenCompraNoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetalleOrdenCompraServiceImpl implements DetalleOrdenCompraServiceInterface {

	private final DetalleOrdenCompraRepository repositorio;
	private final OrdenCompraRepository ordenes;
	private final DetalleOrdenCompraMapper mapper;

	public List<DetalleOrdenCompraResponse> listarPorOrden(UUID id) {
		return repositorio.findAllByOrdenCompraId(id).stream()
				.map(mapper::aResponse)
				.toList();
	}

	public DetalleOrdenCompraResponse obtenerPorId(UUID id) {
		return mapper.aResponse(buscar(id));
	}

	@Transactional
	public DetalleOrdenCompraResponse crear(DetalleOrdenCompraRequest request) {
		return mapper.aResponse(repositorio.save(mapper.aEntidad(request, orden(request.idOrdenCompra()))));
	}

	@Transactional
	public DetalleOrdenCompraResponse actualizar(UUID id, DetalleOrdenCompraRequest request) {
		DetalleOrdenCompra entidad = buscar(id);
		mapper.actualizarEntidad(request, orden(request.idOrdenCompra()), entidad);
		return mapper.aResponse(repositorio.save(entidad));
	}

	@Transactional
	public void eliminar(UUID id) {
		repositorio.delete(buscar(id));
	}

	private DetalleOrdenCompra buscar(UUID id) {
		return repositorio.findById(id)
                .orElseThrow(() -> new DetalleOrdenCompraNoEncontradoException(id));
	}

	private OrdenCompra orden(UUID id) {
		return ordenes.findById(id)
                .orElseThrow(() -> new OrdenCompraNoEncontradaException(id));
	}
}
