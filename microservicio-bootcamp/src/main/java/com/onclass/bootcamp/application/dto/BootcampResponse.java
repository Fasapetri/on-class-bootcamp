package com.onclass.bootcamp.application.dto;

import java.time.LocalDate;
import java.util.List;

public record BootcampResponse(Long id, String nombre, String descripcion, LocalDate fechaLanzamiento, Integer duracion, List<Long> capacidades) {
}
