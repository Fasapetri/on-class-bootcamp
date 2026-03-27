package com.onclass.persona.infraestructure.out.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Table("bootcamp_persona")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BootcampPersonaEntity {

    private Long idPersona;
    private Long idBootcamp;
}
