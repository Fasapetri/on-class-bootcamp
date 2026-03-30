package com.onclass.reporte.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteBootcamp {

    private String id;
    private Long idBootcamp;
    private String nombre;
    private String descripcion;
    private LocalDate fechaLanzamiento;
    private Integer duracion;
    private Integer cantidadCapacidades;
    private Integer cantidadTecnologias;
    private Integer cantidadPersonasInscritas;
    private List<String> nombresCapacidades;
    private List<String> nombresTecnologias;
    private List<PersonaInscritaReporte> personasInscritas;
}
