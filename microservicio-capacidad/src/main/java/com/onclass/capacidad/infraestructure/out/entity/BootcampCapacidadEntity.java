package com.onclass.capacidad.infraestructure.out.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Table("bootcamp_capacidad")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BootcampCapacidadEntity {
    private Long idBootcamp;
    private Long idCapacidad;

}
