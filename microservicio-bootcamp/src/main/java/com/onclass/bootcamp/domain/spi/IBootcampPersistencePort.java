package com.onclass.bootcamp.domain.spi;

import com.onclass.bootcamp.domain.model.Bootcamp;
import reactor.core.publisher.Mono;

public interface IBootcampPersistencePort {

    Mono<Bootcamp> guardarBootcamp(Bootcamp bootcamp);
    Mono<Boolean> existeBootcampPorNombre(String nombreBootcamp);
}
