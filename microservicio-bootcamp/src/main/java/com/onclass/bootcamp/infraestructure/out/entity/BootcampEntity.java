package com.onclass.bootcamp.infraestructure.out.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("bootcamp")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BootcampEntity {

    @Id
    private Long id;
    private String nombre;
    private String descripcion;
    private LocalDate fechaLanzamiento;
    private Integer duracion;
}
