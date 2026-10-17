package com.nexasupplyai.supplier.service.impl;

import com.nexasupplyai.supplier.client.InventoryServiceClient;
import com.nexasupplyai.supplier.dto.request.RecepcionMercaderiaRequest;
import com.nexasupplyai.supplier.dto.external.EntradaInventarioRequest;
import com.nexasupplyai.supplier.dto.response.RecepcionMercaderiaResponse;
import com.nexasupplyai.supplier.entity.OrdenCompra;
import com.nexasupplyai.supplier.entity.RecepcionMercaderia;
import com.nexasupplyai.supplier.entity.DetalleRecepcionMercaderia;
import com.nexasupplyai.supplier.entity.DetalleOrdenCompra;
import com.nexasupplyai.supplier.enums.EstadoRecepcionMercaderia;
import com.nexasupplyai.supplier.enums.EstadoOrdenCompra;
import com.nexasupplyai.supplier.mapper.RecepcionMercaderiaMapper;
import com.nexasupplyai.supplier.repository.OrdenCompraRepository;
import com.nexasupplyai.supplier.repository.RecepcionMercaderiaRepository;
import com.nexasupplyai.supplier.repository.DetalleRecepcionMercaderiaRepository;
import com.nexasupplyai.supplier.repository.DetalleOrdenCompraRepository;
import com.nexasupplyai.supplier.service.interfaces.RecepcionMercaderiaServiceInterface;
import com.nexasupplyai.supplier.exception.RecepcionMercaderiaNoEncontradaException;
import com.nexasupplyai.supplier.exception.OrdenCompraNoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecepcionMercaderiaServiceImpl implements RecepcionMercaderiaServiceInterface {

	private final RecepcionMercaderiaRepository repositorio;
	private final OrdenCompraRepository ordenes;
	private final RecepcionMercaderiaMapper mapper;
	private final InventoryServiceClient inventoryServiceClient;
	private final DetalleRecepcionMercaderiaRepository detallesRecepcion;
	private final DetalleOrdenCompraRepository detallesOrden;

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
		validar(request, null);
		RecepcionMercaderia recepcion = mapper.aEntidad(request, orden(request.idOrdenCompra()));
		recepcion.setEstado(EstadoRecepcionMercaderia.DRAFT);
		return mapper.aResponse(repositorio.save(recepcion));
	}

	@Transactional
	public RecepcionMercaderiaResponse actualizar(UUID id, RecepcionMercaderiaRequest request) {
		RecepcionMercaderia entidad = buscar(id);
		validarBorrador(entidad);
		validar(request, id);
		mapper.actualizarEntidad(request, orden(request.idOrdenCompra()), entidad);
		entidad.setEstado(EstadoRecepcionMercaderia.DRAFT);
		return mapper.aResponse(repositorio.save(entidad));
	}

	@Override
	@Transactional
	public RecepcionMercaderiaResponse postear(UUID id, String actor) {
		RecepcionMercaderia recepcion = repositorio.bloquearPorId(id)
				.orElseThrow(() -> new RecepcionMercaderiaNoEncontradaException(id));
		if (recepcion.getEstado() == EstadoRecepcionMercaderia.POSTED) return mapper.aResponse(recepcion);
		validarBorrador(recepcion);
		OrdenCompra orden = ordenes.bloquearPorId(recepcion.getOrdenCompra().getId())
				.orElseThrow(() -> new OrdenCompraNoEncontradaException(recepcion.getOrdenCompra().getId()));
		if (orden.getEstado() == EstadoOrdenCompra.CANCELLED || orden.getEstado() == EstadoOrdenCompra.DRAFT) {
			throw new IllegalArgumentException("La orden de compra debe estar enviada o aprobada");
		}
		List<DetalleRecepcionMercaderia> lineas = detallesRecepcion.findAllByRecepcionMercaderiaId(id);
		if (lineas.isEmpty()) throw new IllegalArgumentException("La recepción no tiene detalles");
		List<DetalleOrdenCompra> lineasOrden = detallesOrden.bloquearTodasPorOrden(orden.getId());
		Map<UUID, DetalleOrdenCompra> ordenPorId = lineasOrden.stream()
				.collect(Collectors.toMap(DetalleOrdenCompra::getId, Function.identity()));

		for (DetalleRecepcionMercaderia linea : lineas) {
			DetalleOrdenCompra detalleOrden = ordenPorId.get(linea.getDetalleOrdenCompra().getId());
			if (detalleOrden == null || !detalleOrden.getIdProducto().equals(linea.getIdProducto())) {
				throw new IllegalArgumentException("La línea recibida no pertenece a la orden de compra");
			}
			var nuevoAcumulado = detalleOrden.getCantidadRecibida().add(linea.getCantidadRecibida());
			if (nuevoAcumulado.compareTo(detalleOrden.getCantidadPedida()) > 0) {
				throw new IllegalArgumentException("La recepción supera la cantidad pendiente del producto " + detalleOrden.getSkuInstantanea());
			}
		}

		// Inventory Service hace cada entrada idempotente. Si una llamada posterior falla,
		// reintentar este endpoint no duplica las líneas ya publicadas.
		for (DetalleRecepcionMercaderia linea : lineas) {
			inventoryServiceClient.registrarEntrada(new EntradaInventarioRequest(
					recepcion.getIdAlmacen(), linea.getIdProducto(), linea.getCantidadRecibida(),
					"GOODS_RECEIPT", recepcion.getId(),
					"receipt:" + recepcion.getId() + ":" + linea.getId(),
					"Recepción " + recepcion.getNumeroRecepcion(), actor));
		}

		for (DetalleRecepcionMercaderia linea : lineas) {
			DetalleOrdenCompra detalleOrden = ordenPorId.get(linea.getDetalleOrdenCompra().getId());
			detalleOrden.setCantidadRecibida(detalleOrden.getCantidadRecibida().add(linea.getCantidadRecibida()));
		}
		recepcion.setEstado(EstadoRecepcionMercaderia.POSTED);
		recepcion.setRecibidoPor(actor);
		recepcion.setRecibidoEn(OffsetDateTime.now(ZoneOffset.UTC));
		actualizarEstadoOrden(orden, lineasOrden);
		ordenes.saveAndFlush(orden);
		return mapper.aResponse(repositorio.saveAndFlush(recepcion));
	}

	@Transactional
	public void eliminar(UUID id) {
		RecepcionMercaderia recepcion = buscar(id);
		validarBorrador(recepcion);
		repositorio.delete(recepcion);
	}

	private void validarBorrador(RecepcionMercaderia recepcion) {
		if (recepcion.getEstado() != EstadoRecepcionMercaderia.DRAFT) {
			throw new IllegalArgumentException("Solo una recepción en borrador puede modificarse");
		}
	}

	private void actualizarEstadoOrden(OrdenCompra orden, List<DetalleOrdenCompra> lineas) {
		boolean completa = !lineas.isEmpty() && lineas.stream()
				.allMatch(l -> l.getCantidadRecibida().compareTo(l.getCantidadPedida()) >= 0);
		orden.setEstado(completa ? EstadoOrdenCompra.RECEIVED : EstadoOrdenCompra.PARTIALLY_RECEIVED);
		ordenes.save(orden);
	}

	private RecepcionMercaderia buscar(UUID id) {
		return repositorio.findById(id)
                .orElseThrow(() -> new RecepcionMercaderiaNoEncontradaException(id));
	}

	private OrdenCompra orden(UUID id) {
		return ordenes.findById(id)
                .orElseThrow(() -> new OrdenCompraNoEncontradaException(id));
	}

	private void validar(RecepcionMercaderiaRequest request, UUID idActual) {
		repositorio.findByNumeroRecepcion(request.numeroRecepcion())
				.filter(recepcion -> !recepcion.getId().equals(idActual))
				.ifPresent(recepcion -> { throw new IllegalArgumentException("El número de recepción ya existe"); });
		var almacen = inventoryServiceClient.obtenerAlmacen(request.idAlmacen());
		if (almacen == null || !Boolean.TRUE.equals(almacen.activo())) {
			throw new IllegalArgumentException("El almacén no existe o está inactivo en Inventory Service");
		}
	}
}
