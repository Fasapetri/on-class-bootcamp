package com.onclass.capacidad.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginadoCustom<T> {

    private List<T> contenido;
    private int totalPaginas;
    private Long totalElementos;
}
