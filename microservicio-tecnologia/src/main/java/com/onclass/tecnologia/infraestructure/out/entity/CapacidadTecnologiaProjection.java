package com.onclass.tecnologia.infraestructure.out.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CapacidadTecnologiaProjection {

    private Long idCapacidad;
    private Long idTecnologia;
    private String nombreTecnologia;
}
