package com.onclass.bootcamp.infraestructure.in.exception;

import com.onclass.bootcamp.application.dto.ErrorResponse;
import com.onclass.bootcamp.domain.exception.BootcampException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(BootcampException.class)
    public ResponseEntity<ErrorResponse> handleBootcampException(BootcampException ex) {
        ErrorResponse error = new ErrorResponse(ex.getCodigo(), ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarErroresGenericos(Exception ex) {
        ErrorResponse response = new ErrorResponse("ERROR_INTERNO", "Ocurrió un error inesperado en el servidor.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
