package com.nexasupplyai.supplier.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

 @ExceptionHandler({
         ProveedorNoEncontradoException.class,
         ProductoProveedorNoEncontradoException.class,
         OrdenCompraNoEncontradaException.class,
         DetalleOrdenCompraNoEncontradoException.class,
         HistorialEstadoOrdenCompraNoEncontradoException.class,
         RecepcionMercaderiaNoEncontradaException.class,
         DetalleRecepcionMercaderiaNoEncontradoException.class
 })
 public ProblemDetail noEncontrado(RuntimeException e) {
  return ProblemDetail.forStatusAndDetail(
          HttpStatus.NOT_FOUND,
          e.getMessage()
  );
 }

 @ExceptionHandler(IllegalArgumentException.class)
 public ProblemDetail invalido(IllegalArgumentException e) {
  return ProblemDetail.forStatusAndDetail(
          HttpStatus.BAD_REQUEST,
          e.getMessage()
  );
 }

 @ExceptionHandler(MethodArgumentNotValidException.class)
 public ProblemDetail validacion(MethodArgumentNotValidException e) {
  ProblemDetail problema = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "La solicitud contiene campos inválidos");
  Map<String, String> errores = e.getBindingResult().getFieldErrors().stream()
          .collect(Collectors.toMap(error -> error.getField(), error -> error.getDefaultMessage() == null ? "Valor inválido" : error.getDefaultMessage(), (a, b) -> a));
  problema.setProperty("errores", errores);
  return problema;
 }

 @ExceptionHandler(DataIntegrityViolationException.class)
 public ProblemDetail conflicto(DataIntegrityViolationException e) {
  return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "La operación infringe una restricción de datos");
 }

 @ExceptionHandler(RestClientException.class)
 public ProblemDetail dependenciaNoDisponible(RestClientException e) {
  return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_GATEWAY, "No fue posible consultar un servicio dependiente");
 }
}
