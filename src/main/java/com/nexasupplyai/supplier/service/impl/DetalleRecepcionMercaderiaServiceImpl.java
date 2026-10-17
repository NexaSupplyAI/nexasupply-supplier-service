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
import com.nexasupplyai.supplier.exception.DetalleRecepcionMercaderiaNoEncontradoException;
import com.nexasupplyai.supplier.exception.RecepcionMercaderiaNoEncontradaException;
import com.nexasupplyai.supplier.exception.DetalleOrdenCompraNoEncontradoException;
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
		RecepcionMercaderia recepcion = recepcion(request.idRecepcionMercaderia());
		validarBorrador(recepcion);
		DetalleOrdenCompra detalle = detalle(request.idDetalleOrdenCompra());
		if (!detalle.getOrdenCompra().getId().equals(recepcion.getOrdenCompra().getId())) {
			throw new IllegalArgumentException("El detalle no pertenece a la orden de compra de la recepción");
		}
		if (!detalle.getIdProducto().equals(request.idProducto())) {
			throw new IllegalArgumentException("El producto no coincide con el detalle de la orden de compra");
		}
		if (request.cantidadRecibida().compareTo(detalle.getCantidadPedida()) > 0) {
			throw new IllegalArgumentException("La cantidad recibida no puede superar la cantidad pedida");
		}
		repositorio.findByRecepcionMercaderiaIdAndDetalleOrdenCompraId(
				request.idRecepcionMercaderia(), request.idDetalleOrdenCompra())
				.ifPresent(existente -> { throw new IllegalArgumentException("El detalle ya existe en la recepción"); });
		return mapper.aResponse(repositorio.save(mapper.aEntidad(
				request,
				recepcion,
				detalle)));
	}

	@Transactional
	public void eliminar(UUID id) {
		DetalleRecepcionMercaderia detalle = buscar(id);
		validarBorrador(detalle.getRecepcionMercaderia());
		repositorio.delete(detalle);
	}

	private DetalleRecepcionMercaderia buscar(UUID id) {
		return repositorio.findById(id)
                .orElseThrow(() -> new DetalleRecepcionMercaderiaNoEncontradoException(id));
	}

	private RecepcionMercaderia recepcion(UUID id) {
		return recepciones.findById(id)
                .orElseThrow(() -> new RecepcionMercaderiaNoEncontradaException(id));
	}

	private DetalleOrdenCompra detalle(UUID id) {
		return detalles.findById(id)
                .orElseThrow(() -> new DetalleOrdenCompraNoEncontradoException(id));
	}

	private void validarBorrador(RecepcionMercaderia recepcion) {
		if (recepcion.getEstado() != com.nexasupplyai.supplier.enums.EstadoRecepcionMercaderia.DRAFT) {
			throw new IllegalArgumentException("Los detalles solo pueden modificarse en una recepción borrador");
		}
	}
}
