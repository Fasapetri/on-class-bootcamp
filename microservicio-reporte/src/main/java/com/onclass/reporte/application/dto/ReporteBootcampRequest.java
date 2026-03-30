package com.onclass.reporte.application.dto;

import com.onclass.reporte.domain.model.PersonaInscritaReporte;

import java.time.LocalDate;
import java.util.List;

public record ReporteBootcampRequest(Long idBootcamp, String nombre, String descripcion, LocalDate fechaLanzamiento, Integer duracion, Integer cantidadCapacidades, Integer cantidadTecnologias, Integer cantidadPersonasInscritas, List<String> nombresCapacidades, List<String> nombresTecnologias, List<PersonaInscritaReporte> personasInscritas) {
}
