package com.nexasupplyai.supplier.service.impl;

import com.nexasupplyai.supplier.dto.response.HistorialEstadoOrdenCompraResponse;
import com.nexasupplyai.supplier.entity.HistorialEstadoOrdenCompra;
import com.nexasupplyai.supplier.mapper.HistorialEstadoOrdenCompraMapper;
import com.nexasupplyai.supplier.repository.HistorialEstadoOrdenCompraRepository;
import com.nexasupplyai.supplier.service.interfaces.HistorialEstadoOrdenCompraServiceInterface;
import com.nexasupplyai.supplier.exception.HistorialEstadoOrdenCompraNoEncontradoException;
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
	private final HistorialEstadoOrdenCompraMapper mapper;

	public List<HistorialEstadoOrdenCompraResponse> listarPorOrden(UUID id) {
		return repositorio.findAllByOrdenCompraIdOrderByCambiadoEnDesc(id).stream()
				.map(mapper::aResponse)
				.toList();
	}

	public HistorialEstadoOrdenCompraResponse obtenerPorId(UUID id) {
		HistorialEstadoOrdenCompra entidad = repositorio.findById(id)
                .orElseThrow(() -> new HistorialEstadoOrdenCompraNoEncontradoException(id));
		return mapper.aResponse(entidad);
	}
}
