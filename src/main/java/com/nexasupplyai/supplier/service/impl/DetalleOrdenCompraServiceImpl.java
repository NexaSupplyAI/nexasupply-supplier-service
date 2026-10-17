package com.nexasupplyai.supplier.service.impl;

import com.nexasupplyai.supplier.client.InventoryServiceClient;
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
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetalleOrdenCompraServiceImpl implements DetalleOrdenCompraServiceInterface {

	private final DetalleOrdenCompraRepository repositorio;
	private final OrdenCompraRepository ordenes;
	private final DetalleOrdenCompraMapper mapper;
	private final InventoryServiceClient inventoryServiceClient;

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
		validar(request, null);
		OrdenCompra orden = orden(request.idOrdenCompra());
		validarBorrador(orden);
		DetalleOrdenCompra detalle = mapper.aEntidad(request, orden);
		detalle.setCantidadRecibida(java.math.BigDecimal.ZERO);
		return mapper.aResponse(repositorio.save(detalle));
	}

	@Transactional
	public DetalleOrdenCompraResponse actualizar(UUID id, DetalleOrdenCompraRequest request) {
		DetalleOrdenCompra entidad = buscar(id);
		validar(request, id);
		OrdenCompra orden = orden(request.idOrdenCompra());
		validarBorrador(orden);
		mapper.actualizarEntidad(request, orden, entidad);
		return mapper.aResponse(repositorio.save(entidad));
	}

	@Transactional
	public void eliminar(UUID id) {
		DetalleOrdenCompra detalle = buscar(id);
		validarBorrador(detalle.getOrdenCompra());
		repositorio.delete(detalle);
	}

	private DetalleOrdenCompra buscar(UUID id) {
		return repositorio.findById(id)
                .orElseThrow(() -> new DetalleOrdenCompraNoEncontradoException(id));
	}

	private OrdenCompra orden(UUID id) {
		return ordenes.findById(id)
                .orElseThrow(() -> new OrdenCompraNoEncontradaException(id));
	}

	private void validar(DetalleOrdenCompraRequest request, UUID idActual) {
		repositorio.findByOrdenCompraIdAndIdProducto(request.idOrdenCompra(), request.idProducto())
				.filter(detalle -> !detalle.getId().equals(idActual))
				.ifPresent(detalle -> { throw new IllegalArgumentException("El producto ya existe en la orden de compra"); });
		var producto = inventoryServiceClient.obtenerProducto(request.idProducto());
		if (producto == null || !Boolean.TRUE.equals(producto.activo())) {
			throw new IllegalArgumentException("El producto no existe o está inactivo en Inventory Service");
		}
		if (!Objects.equals(producto.sku(), request.skuInstantanea())
				|| !Objects.equals(producto.nombre(), request.nombreProductoInstantanea())) {
			throw new IllegalArgumentException("El SKU o nombre del producto no coincide con Inventory Service");
		}
	}

	private void validarBorrador(OrdenCompra orden) {
		if (orden.getEstado() != com.nexasupplyai.supplier.enums.EstadoOrdenCompra.DRAFT) {
			throw new IllegalArgumentException("Los detalles solo pueden modificarse mientras la orden está en borrador");
		}
	}
}
