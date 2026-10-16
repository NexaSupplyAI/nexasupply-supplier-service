package com.nexasupplyai.supplier.service.impl;

import com.nexasupplyai.supplier.dto.request.OrdenCompraRequest;
import com.nexasupplyai.supplier.dto.response.OrdenCompraResponse;
import com.nexasupplyai.supplier.entity.OrdenCompra;
import com.nexasupplyai.supplier.entity.Proveedor;
import com.nexasupplyai.supplier.mapper.OrdenCompraMapper;
import com.nexasupplyai.supplier.repository.OrdenCompraRepository;
import com.nexasupplyai.supplier.repository.ProveedorRepository;
import com.nexasupplyai.supplier.service.interfaces.OrdenCompraServiceInterface;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrdenCompraServiceImpl implements OrdenCompraServiceInterface {

	private final OrdenCompraRepository repositorio;
	private final ProveedorRepository proveedores;
	private final OrdenCompraMapper mapper;

	public List<OrdenCompraResponse> listarPorProveedor(UUID id) {
		return repositorio.findAllByProveedorId(id).stream()
				.map(mapper::aResponse)
				.toList();
	}

	public OrdenCompraResponse obtenerPorId(UUID id) {
		return mapper.aResponse(buscar(id));
	}

	@Transactional
	public OrdenCompraResponse crear(OrdenCompraRequest request) {
		return mapper.aResponse(repositorio.save(mapper.aEntidad(request, proveedor(request.idProveedor()))));
	}

	@Transactional
	public OrdenCompraResponse actualizar(UUID id, OrdenCompraRequest request) {
		OrdenCompra entidad = buscar(id);
		mapper.actualizarEntidad(request, proveedor(request.idProveedor()), entidad);
		return mapper.aResponse(repositorio.save(entidad));
	}

	@Transactional
	public void eliminar(UUID id) {
		repositorio.delete(buscar(id));
	}

	private OrdenCompra buscar(UUID id) {
		return repositorio.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Orden de compra no encontrada: " + id));
	}

	private Proveedor proveedor(UUID id) {
		return proveedores.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado: " + id));
	}
}
