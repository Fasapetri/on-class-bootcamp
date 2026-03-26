package com.onclass.bootcamp.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Capacidad {

    private Long id;
    private String nombre;
    private List<Tecnologia> tecnologias;
}
