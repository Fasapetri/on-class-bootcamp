package com.onclass.reporte.infraestructure.out.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "reportes_bootcamp")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteBootcampDocument {

    @Id
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
    private List<PersonaInscritaReporteDocument> personasInscritas;
}
