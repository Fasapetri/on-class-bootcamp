package com.onclass.persona.infraestructure.in.exception;

import com.onclass.persona.application.dto.ErrorResponse;
import com.onclass.persona.domain.exception.PersonaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(PersonaException.class)
    public ResponseEntity<ErrorResponse> handlePersonaException(PersonaException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getCodigo(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarErroresGenericos(Exception ex) {
        ErrorResponse response = new ErrorResponse("ERROR_INTERNO", "Ocurrió un error inesperado en el servidor.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}
