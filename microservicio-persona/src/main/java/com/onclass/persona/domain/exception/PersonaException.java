package com.onclass.persona.domain.exception;

public class PersonaException extends RuntimeException{

    private final String codigo;

    public PersonaException(PersonaErrorMessage personaErrorMessage) {
        super(personaErrorMessage.getMensaje());
        this.codigo = personaErrorMessage.getCodigo();
    }

    public String getCodigo() {
        return codigo;
    }
}
