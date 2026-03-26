package com.onclass.capacidad.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BootcampCapacidadProjection {

    private Long idBootcamp;
    private Long idCapacidad;
    private String nombreCapacidad;
    private String descripcionCapacidad;
}
