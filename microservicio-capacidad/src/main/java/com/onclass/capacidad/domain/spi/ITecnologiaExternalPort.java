package com.onclass.capacidad.domain.spi;

import reactor.core.publisher.Mono;

import java.util.List;

public interface ITecnologiaExternalPort {

    Mono<Boolean> guardarRelacionCapacidadTecnologia(Long idCapacidad, List<Long> tecnologias);
    Mono<Boolean> existenTecnologias(List<Long> tecnologias);
}
