package com.onclass.tecnologia.infraestructure.out.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Table("capacidad_tecnologia")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CapacidadTecnologiaEntity {

    private Long idCapacidad;
    private Long idTecnologia;
}
