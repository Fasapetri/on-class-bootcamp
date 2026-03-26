package com.onclass.persona.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Persona {

    private Long id;
    private String nombre;
    private String correo;
    private Integer edad;
}
