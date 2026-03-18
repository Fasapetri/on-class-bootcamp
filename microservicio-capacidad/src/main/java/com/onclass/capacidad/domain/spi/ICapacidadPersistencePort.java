package com.onclass.capacidad.domain.spi;

import com.onclass.capacidad.domain.model.Capacidad;
import com.onclass.capacidad.domain.model.PaginadoCustom;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ICapacidadPersistencePort {

    Mono<Capacidad> guardarCapacidad(Capacidad capacidad);
    Mono<PaginadoCustom<Capacidad>> listarCapacidadesPaginadas(int pagina, int tamanio, String direccionAscDesc);
    Flux<Capacidad> obtenerCapacidadesPorIds(List<Long> ids);
}
