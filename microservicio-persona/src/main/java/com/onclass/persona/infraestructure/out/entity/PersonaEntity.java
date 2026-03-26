package com.onclass.persona.infraestructure.out.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("persona")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonaEntity {

    @Id
    private Long id;
    private String nombre;
    private String correo;
    private Integer edad;
}
