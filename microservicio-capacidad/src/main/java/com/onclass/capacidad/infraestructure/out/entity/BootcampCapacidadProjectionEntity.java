package com.onclass.capacidad.infraestructure.out.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BootcampCapacidadProjectionEntity {

    private Long idBootcamp;
    private Long idCapacidad;
    private String nombreCapacidad;
    private String descripcionCapacidad;
}
