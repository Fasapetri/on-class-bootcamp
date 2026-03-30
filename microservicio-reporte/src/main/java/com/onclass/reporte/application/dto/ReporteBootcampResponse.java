package com.onclass.reporte.application.dto;

import java.time.LocalDate;

public record ReporteBootcampResponse(String id, Long idBootcamp, String nombre, String descripcion, LocalDate fechaLanzamiento, Integer duracion, Integer cantidadCapacidades, Integer cantidadTecnologias, Integer cantidadPersonasInscritas) {
}
