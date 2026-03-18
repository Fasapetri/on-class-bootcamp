package com.onclass.tecnologia.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginaCustom <T>{

    private List<T> contenido;
    private int totalPaginas;
    private Long totalElementos;
}
