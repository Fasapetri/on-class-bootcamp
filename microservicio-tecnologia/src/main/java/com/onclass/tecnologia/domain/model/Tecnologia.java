package com.onclass.tecnologia.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tecnologia {

    private Long id;
    private String nombre;
    private String descripcion;
}
