package com.onclass.capacidad.domain.exception;

public enum CapacidadErrorMessage {

    TECNOLOGIAS_MINIMAS("CAP-001", "La capacidad debe tener al menos 3 tecnologías asociadas."),
    TECNOLOGIAS_MAXIMAS("CAP-002", "La capacidad puede tener máximo 20 tecnologías."),
    TECNOLOGIAS_DUPLICADAS("CAP-003", "La capacidad no puede tener tecnologías repetidas."),
    TECNOLOGIA_NO_EXISTE("CAP-004", "Una o más tecnologías enviadas no existen en el sistema.");

    private final String code;
    private final String message;

    CapacidadErrorMessage(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
