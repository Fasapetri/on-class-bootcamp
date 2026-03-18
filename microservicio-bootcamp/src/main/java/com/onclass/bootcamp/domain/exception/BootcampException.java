package com.onclass.bootcamp.domain.exception;

public class BootcampException extends RuntimeException{

    private final String codigo;

    public BootcampException(BootcampErrorMessage bootcampErrorMessage) {
        super(bootcampErrorMessage.getMensaje());
        this.codigo = bootcampErrorMessage.getCodigo();
    }

    public String getCodigo() {
        return codigo;
    }
}
