package com.onclass.bootcamp.domain.spi;

import reactor.core.publisher.Mono;

import java.util.List;

public interface ICapacidadExternalPort {

    Mono<Boolean> guardarRelacionBootcampCapacidad(Long idBootcamp, List<Long> capacidades);
    Mono<Boolean> existenCapacidades(List<Long> capacidades);
}
