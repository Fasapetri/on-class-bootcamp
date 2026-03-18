package com.onclass.capacidad.application.dto;

import java.util.List;

public record CapacidadResponse(Long id, String nombre, String descripcion, List<Long> tecnologias) {
}
