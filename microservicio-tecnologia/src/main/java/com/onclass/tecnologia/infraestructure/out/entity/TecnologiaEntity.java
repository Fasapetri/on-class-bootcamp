package com.onclass.tecnologia.infraestructure.out.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("tecnologia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TecnologiaEntity {

    @Id
    private long id;
    private String nombre;
    private String descripcion;
}
