package com.onclass.persona.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BootcampFecha {

    private Long id;
    private LocalDate fechaLanzamiento;
    private Integer duracion;
}
