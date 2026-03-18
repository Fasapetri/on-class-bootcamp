package com.onclass.capacidad.domain.spi;

import com.onclass.capacidad.domain.model.Capacidad;
import reactor.core.publisher.Mono;

public interface ICapacidadPersistencePort {

    Mono<Capacidad> guardarCapacidad(Capacidad capacidad);
}
