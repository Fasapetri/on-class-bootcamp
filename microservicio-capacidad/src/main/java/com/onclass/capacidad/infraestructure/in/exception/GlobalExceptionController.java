package com.onclass.capacidad.infraestructure.in.exception;

import com.onclass.capacidad.application.dto.ErrorResponse;
import com.onclass.capacidad.domain.exception.CapacidadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(CapacidadException.class)
    public ResponseEntity<ErrorResponse> handleCapacidadException(CapacidadException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getCode(), ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarErroresGenericos(Exception ex) {
        ErrorResponse response = new ErrorResponse("ERROR_INTERNO", "Ocurrió un error inesperado en el servidor.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
