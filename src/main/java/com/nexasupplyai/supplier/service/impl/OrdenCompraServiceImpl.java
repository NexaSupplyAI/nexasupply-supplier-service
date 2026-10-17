package com.nexasupplyai.supplier.service.impl;

import com.nexasupplyai.supplier.dto.request.OrdenCompraRequest;
import com.nexasupplyai.supplier.dto.response.OrdenCompraResponse;
import com.nexasupplyai.supplier.entity.OrdenCompra;
import com.nexasupplyai.supplier.entity.Proveedor;
import com.nexasupplyai.supplier.entity.HistorialEstadoOrdenCompra;
import com.nexasupplyai.supplier.enums.EstadoOrdenCompra;
import com.nexasupplyai.supplier.mapper.OrdenCompraMapper;
import com.nexasupplyai.supplier.repository.OrdenCompraRepository;
import com.nexasupplyai.supplier.repository.ProveedorRepository;
import com.nexasupplyai.supplier.repository.HistorialEstadoOrdenCompraRepository;
import com.nexasupplyai.supplier.service.interfaces.OrdenCompraServiceInterface;
import com.nexasupplyai.supplier.exception.OrdenCompraNoEncontradaException;
import com.nexasupplyai.supplier.exception.ProveedorNoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrdenCompraServiceImpl implements OrdenCompraServiceInterface {

	private final OrdenCompraRepository repositorio;
	private final ProveedorRepository proveedores;
	private final OrdenCompraMapper mapper;
	private final HistorialEstadoOrdenCompraRepository historial;

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
		OrdenCompra orden = mapper.aEntidad(request, proveedor(request.idProveedor()));
		orden.setEstado(EstadoOrdenCompra.DRAFT);
		return mapper.aResponse(repositorio.save(orden));
	}

	@Transactional
	public OrdenCompraResponse actualizar(UUID id, OrdenCompraRequest request) {
		OrdenCompra entidad = buscar(id);
		validarBorrador(entidad);
        validar(request, id);
		mapper.actualizarEntidad(request, proveedor(request.idProveedor()), entidad);
		return mapper.aResponse(repositorio.save(entidad));
	}

	@Transactional
	public void eliminar(UUID id) {
		OrdenCompra orden = buscar(id);
		validarBorrador(orden);
		repositorio.delete(orden);
	}

	@Transactional
	public OrdenCompraResponse enviar(UUID id, String actor) {
		OrdenCompra orden = bloquear(id);
		if (orden.getEstado() == EstadoOrdenCompra.SUBMITTED) return mapper.aResponse(orden);
		if (orden.getEstado() != EstadoOrdenCompra.DRAFT) {
			throw new IllegalArgumentException("Solo una orden en borrador puede enviarse");
		}
		cambiarEstado(orden, EstadoOrdenCompra.SUBMITTED, actor, "Orden enviada para aprobación");
		return mapper.aResponse(repositorio.saveAndFlush(orden));
	}

	@Transactional
	public OrdenCompraResponse aprobar(UUID id, String actor) {
		OrdenCompra orden = bloquear(id);
		if (orden.getEstado() == EstadoOrdenCompra.APPROVED) return mapper.aResponse(orden);
		if (orden.getEstado() != EstadoOrdenCompra.SUBMITTED) {
			throw new IllegalArgumentException("Solo una orden enviada puede aprobarse");
		}
		cambiarEstado(orden, EstadoOrdenCompra.APPROVED, actor, "Orden aprobada");
		orden.setAprobadoPor(actor);
		orden.setAprobadoEn(OffsetDateTime.now(ZoneOffset.UTC));
		return mapper.aResponse(repositorio.saveAndFlush(orden));
	}

	@Transactional
	public OrdenCompraResponse cancelar(UUID id, String actor, String motivo) {
		OrdenCompra orden = bloquear(id);
		if (orden.getEstado() == EstadoOrdenCompra.CANCELLED) return mapper.aResponse(orden);
		if (orden.getEstado() == EstadoOrdenCompra.RECEIVED || orden.getEstado() == EstadoOrdenCompra.PARTIALLY_RECEIVED) {
			throw new IllegalArgumentException("Una orden con recepciones no puede cancelarse");
		}
		cambiarEstado(orden, EstadoOrdenCompra.CANCELLED, actor,
				motivo == null || motivo.isBlank() ? "Orden cancelada" : motivo);
		return mapper.aResponse(repositorio.saveAndFlush(orden));
	}

	private OrdenCompra buscar(UUID id) {
		return repositorio.findById(id)
                .orElseThrow(() -> new OrdenCompraNoEncontradaException(id));
	}

	private OrdenCompra bloquear(UUID id) {
		return repositorio.bloquearPorId(id).orElseThrow(() -> new OrdenCompraNoEncontradaException(id));
	}

	private void validarBorrador(OrdenCompra orden) {
		if (orden.getEstado() != EstadoOrdenCompra.DRAFT) {
			throw new IllegalArgumentException("Solo una orden en borrador puede modificarse o eliminarse");
		}
	}

	private void cambiarEstado(OrdenCompra orden, EstadoOrdenCompra nuevo, String actor, String motivo) {
		HistorialEstadoOrdenCompra evento = new HistorialEstadoOrdenCompra();
		evento.setOrdenCompra(orden);
		evento.setEstadoAnterior(orden.getEstado());
		evento.setNuevoEstado(nuevo);
		evento.setMotivo(motivo);
		evento.setCambiadoPor(actor);
		historial.save(evento);
		orden.setEstado(nuevo);
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
