package com.onclass.tecnologia.domain.exception;

public class TecnologiaException extends RuntimeException{

    private final String codigo;

    public TecnologiaException(TecnologiaErrorMessage error) {
        super(error.getMensaje());
        this.codigo = error.getCodigo();
    }

    public String getCodigo() {
        return codigo;
    }
}
