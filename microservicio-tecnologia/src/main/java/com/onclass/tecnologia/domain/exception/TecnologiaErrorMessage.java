package com.onclass.tecnologia.domain.exception;

public enum TecnologiaErrorMessage {
    NOMBRE_REQUERIDO("TEC-001", "El nombre de la tecnología es obligatorio."),
    NOMBRE_EXCEDE_LONGITUD("TEC-002", "El nombre debe tener máximo 50 caracteres."),
    DESCRIPCION_REQUERIDA("TEC-003", "La descripción de la tecnología es obligatoria."),
    DESCRIPCION_EXCEDE_LONGITUD("TEC-004", "La descripción debe tener máximo 90 caracteres."),
    TECNOLOGIA_DUPLICADA("TEC-005", "Ya existe una tecnología registrada con ese nombre.");

    private final String codigo;
    private final String mensaje;

    TecnologiaErrorMessage(String codigo, String mensaje) {
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
