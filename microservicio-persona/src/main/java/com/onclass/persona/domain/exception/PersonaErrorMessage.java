package com.onclass.persona.domain.exception;

public enum PersonaErrorMessage {

    MAXIMO_INSCRIPCIONES("PER-001", "No puedes inscribirte a más de 5 bootcamps al mismo tiempo."),
    CRUCE_DE_FECHAS("PER-002", "El bootcamp se cruza en fechas con otro al que ya estás inscrito."),
    BOOTCAMP_NO_EXISTE("PER-003", "El bootcamp al que intentas inscribirte no existe.");

    private final String codigo;
    private final String mensaje;

    PersonaErrorMessage(String codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getMensaje() {
        return mensaje;
    }
}
