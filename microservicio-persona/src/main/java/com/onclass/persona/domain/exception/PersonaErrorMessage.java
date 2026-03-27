package com.onclass.persona.domain.exception;

public enum PersonaErrorMessage {

    MAXIMO_INSCRIPCIONES("PER-001", "No puedes inscribirte a más de 5 bootcamps al mismo tiempo."),
    CRUCE_DE_FECHAS("PER-002", "El bootcamp se cruza en fechas con otro al que ya estás inscrito."),
    BOOTCAMP_NO_EXISTE("PER-003", "El bootcamp al que intentas inscribirte no existe."),
    YA_INSCRITO("PER-004", "Ya te encuentras inscrito en uno o más de los bootcamps solicitados."),
    PERSONA_NO_EXISTE("PER-005", "La persona indicada no se encuentra registrada en el sistema.");

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
