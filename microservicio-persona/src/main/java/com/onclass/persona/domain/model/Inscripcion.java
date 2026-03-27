package com.onclass.persona.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inscripcion {

    private Long idPersona;
    private List<Long> idsBootcamps;
}
