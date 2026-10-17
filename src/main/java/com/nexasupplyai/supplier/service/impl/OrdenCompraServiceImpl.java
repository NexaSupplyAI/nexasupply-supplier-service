package com.nexasupplyai.supplier.service.impl;

import com.nexasupplyai.supplier.dto.request.OrdenCompraRequest;
import com.nexasupplyai.supplier.dto.response.OrdenCompraResponse;
import com.nexasupplyai.supplier.entity.OrdenCompra;
import com.nexasupplyai.supplier.entity.Proveedor;
import com.nexasupplyai.supplier.mapper.OrdenCompraMapper;
import com.nexasupplyai.supplier.repository.OrdenCompraRepository;
import com.nexasupplyai.supplier.repository.ProveedorRepository;
import com.nexasupplyai.supplier.service.interfaces.OrdenCompraServiceInterface;
import com.nexasupplyai.supplier.exception.OrdenCompraNoEncontradaException;
import com.nexasupplyai.supplier.exception.ProveedorNoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrdenCompraServiceImpl implements OrdenCompraServiceInterface {

	private final OrdenCompraRepository repositorio;
	private final ProveedorRepository proveedores;
	private final OrdenCompraMapper mapper;

	public List<OrdenCompraResponse> listar() {
		return repositorio.findAll().stream().map(mapper::aResponse).toList();
	}

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
        validar(request, null);
		return mapper.aResponse(repositorio.save(mapper.aEntidad(request, proveedor(request.idProveedor()))));
	}

	@Transactional
	public OrdenCompraResponse actualizar(UUID id, OrdenCompraRequest request) {
		OrdenCompra entidad = buscar(id);
        validar(request, id);
		mapper.actualizarEntidad(request, proveedor(request.idProveedor()), entidad);
		return mapper.aResponse(repositorio.save(entidad));
	}

	@Transactional
	public void eliminar(UUID id) {
		repositorio.delete(buscar(id));
	}

	private OrdenCompra buscar(UUID id) {
		return repositorio.findById(id)
                .orElseThrow(() -> new OrdenCompraNoEncontradaException(id));
	}

	private Proveedor proveedor(UUID id) {
		return proveedores.findById(id)
                .orElseThrow(() -> new ProveedorNoEncontradoException(id));
	}

    private void validar(OrdenCompraRequest request, UUID idActual) {
        repositorio.findByNumeroOrdenCompra(request.numeroOrdenCompra())
                .filter(orden -> !orden.getId().equals(idActual))
                .ifPresent(orden -> { throw new IllegalArgumentException("El número de orden de compra ya existe"); });
        BigDecimal totalCalculado = request.subtotal()
                .add(request.montoImpuesto())
                .subtract(request.montoDescuento());
        if (request.montoDescuento().compareTo(request.subtotal().add(request.montoImpuesto())) > 0) {
            throw new IllegalArgumentException("El descuento no puede superar el subtotal más impuestos");
        }
        if (totalCalculado.compareTo(request.montoTotal()) != 0) {
            throw new IllegalArgumentException("El total no coincide con subtotal + impuestos - descuento");
        }
    }
}
