package com.nexasupplyai.supplier.service.impl;

import com.nexasupplyai.supplier.dto.request.ProveedorRequest;
import com.nexasupplyai.supplier.dto.response.ProveedorResponse;
import com.nexasupplyai.supplier.entity.Proveedor;
import com.nexasupplyai.supplier.mapper.ProveedorMapper;
import com.nexasupplyai.supplier.repository.ProveedorRepository;
import com.nexasupplyai.supplier.service.interfaces.ProveedorServiceInterface;
import com.nexasupplyai.supplier.exception.ProveedorNoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProveedorServiceImpl implements ProveedorServiceInterface {

    private final ProveedorRepository repositorio;
    private final ProveedorMapper mapper;

    public List<ProveedorResponse> listar() {
        return repositorio.findAll().stream()
                .map(mapper::aResponse)
                .toList();
    }

    public ProveedorResponse obtenerPorId(UUID id) {
        return mapper.aResponse(buscar(id));
    }

    @Transactional
    public ProveedorResponse crear(ProveedorRequest request) {
        validarUnicidad(request, null);
        return mapper.aResponse(repositorio.save(mapper.aEntidad(request)));
    }

    @Transactional
    public ProveedorResponse actualizar(UUID id, ProveedorRequest request) {
        Proveedor entidad = buscar(id);
        validarUnicidad(request, id);
        mapper.actualizarEntidad(request, entidad);
        return mapper.aResponse(repositorio.save(entidad));
    }

    @Transactional
    public void eliminar(UUID id) {
        repositorio.delete(buscar(id));
    }

    private Proveedor buscar(UUID id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new ProveedorNoEncontradoException(id));
    }

    private void validarUnicidad(ProveedorRequest request, UUID idActual) {
        repositorio.findByCodigoProveedor(request.codigoProveedor())
                .filter(proveedor -> !proveedor.getId().equals(idActual))
                .ifPresent(proveedor -> { throw new IllegalArgumentException("El código de proveedor ya existe"); });
        if (request.numeroFiscal() != null && !request.numeroFiscal().isBlank()) {
            repositorio.findByNumeroFiscal(request.numeroFiscal())
                    .filter(proveedor -> !proveedor.getId().equals(idActual))
                    .ifPresent(proveedor -> { throw new IllegalArgumentException("El número fiscal ya existe"); });
        }
        if (request.email() != null && !request.email().isBlank()) {
            repositorio.findByEmailIgnoreCase(request.email())
                    .filter(proveedor -> !proveedor.getId().equals(idActual))
                    .ifPresent(proveedor -> { throw new IllegalArgumentException("El correo del proveedor ya existe"); });
        }
    }
}
