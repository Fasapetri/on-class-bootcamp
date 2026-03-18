package com.onclass.capacidad.domain.exception;

public class CapacidadException extends RuntimeException{

    private final String code;

    public CapacidadException(CapacidadErrorMessage capacidadErrorMessage) {
        super(capacidadErrorMessage.getMessage());
        this.code = capacidadErrorMessage.getCode();
    }

    public String getCode() {
        return code;
    }
}
