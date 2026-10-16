package com.nexasupplyai.supplier.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

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
}