package com.onclass.capacidad.infraestructure.out.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table("capacidad")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CapacidadEntity {

    @Id
    private Long id;
    private String nombre;
    private String descripcion;
}
