package com.onclass.tecnologia.application.dto;

import java.util.List;

public record RelacionCapacidadTecnologiaRequest(Long idCapacidad, List<Long> tecnologias) {
}
