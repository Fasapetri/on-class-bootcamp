package com.onclass.bootcamp.domain.exception;

public enum BootcampErrorMessage {

    CAPACIDADES_MINIMAS("BOOT-001", "El bootcamp debe tener al menos 1 capacidad asociada."),
    CAPACIDADES_MAXIMAS("BOOT-002", "El bootcamp puede tener máximo 4 capacidades."),
    CAPACIDADES_DUPLICADAS("BOOT-003", "El bootcamp no puede tener capacidades repetidas."),
    CAPACIDAD_NO_EXISTE("BOOT-004", "Una o más capacidades enviadas no existen en el sistema."),
    BOOTCAMP_DUPLICADO("BOOT-005", "Ya existe un Bootcamp registrado con ese nombre.");

    private final String codigo;
    private final String mensaje;

    BootcampErrorMessage(String codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getCodigo() {
        return codigo;
    }
}
