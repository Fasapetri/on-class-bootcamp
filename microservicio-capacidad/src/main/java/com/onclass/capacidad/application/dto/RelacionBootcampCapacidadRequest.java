package com.onclass.capacidad.application.dto;

import java.util.List;

public record RelacionBootcampCapacidadRequest(Long idBootcamp, List<Long> capacidades) {
}
