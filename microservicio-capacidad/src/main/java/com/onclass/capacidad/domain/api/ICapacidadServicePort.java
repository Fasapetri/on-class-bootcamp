package com.onclass.capacidad.domain.api;

import com.onclass.capacidad.domain.model.Capacidad;
import reactor.core.publisher.Mono;

public interface ICapacidadServicePort {

    Mono<Capacidad> guardarCapacidad(Capacidad capacidad);
}
