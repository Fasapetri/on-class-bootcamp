package com.onclass.tecnologia.domain.api;

import com.onclass.tecnologia.domain.model.Tecnologia;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ITecnologiaServicePort {

    Mono<Tecnologia> guardarTecnologia(Tecnologia tecnologia);
    Mono<Boolean> existenTodasLasTecnologias(List<Long> idsTecnologia);
    Mono<Void> guardarRelacionCapacidadTecnologia(Long idCapacidad, List<Long> tecnologias);

}
