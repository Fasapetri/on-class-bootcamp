package com.onclass.capacidad.application.dto;

import java.util.List;

public record CapacidadRequest(String nombre, String descripcion, List<Long> tecnologias) {
}
