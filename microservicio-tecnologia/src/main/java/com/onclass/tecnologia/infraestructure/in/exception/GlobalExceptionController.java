package com.onclass.tecnologia.infraestructure.in.exception;

import com.onclass.tecnologia.application.dto.ErrorResponse;
import com.onclass.tecnologia.domain.exception.TecnologiaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(TecnologiaException.class)
    public ResponseEntity<ErrorResponse> handleTecnologiaException(TecnologiaException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getCodigo(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getCodigo()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarErroresGenericos(Exception ex) {
        ErrorResponse response = new ErrorResponse("ERROR_INTERNO", "Ocurrió un error inesperado en el servidor.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
