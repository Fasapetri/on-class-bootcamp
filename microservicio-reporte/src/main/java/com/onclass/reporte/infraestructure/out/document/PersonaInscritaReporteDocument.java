package com.onclass.reporte.infraestructure.out.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reportes_persona_inscrita")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonaInscritaReporteDocument {

    private String id;
    private String nombre;
    private String correo;
}
