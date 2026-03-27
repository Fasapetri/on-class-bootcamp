package com.onclass.bootcamp.application.dto;

import java.time.LocalDate;

public record BootcampFechaResponse(Long id, LocalDate fechaLanzamiento, Integer duracion) {
}
