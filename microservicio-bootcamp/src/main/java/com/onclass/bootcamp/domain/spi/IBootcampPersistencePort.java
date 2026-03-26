package com.onclass.bootcamp.domain.spi;

import com.onclass.bootcamp.domain.model.Bootcamp;
import com.onclass.bootcamp.domain.model.BootcampDetalle;
import com.onclass.bootcamp.domain.model.PaginaCustom;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IBootcampPersistencePort {

    Mono<Bootcamp> guardarBootcamp(Bootcamp bootcamp);
    Mono<Boolean> existeBootcampPorNombre(String nombreBootcamp);
    Mono<PaginaCustom<Bootcamp>> listarBootcampsPaginados(int numeroPagina, int tamanioPagina, String orden);
    Flux<Bootcamp> obtenerBootcampsPorIds(List<Long> ids);
    Mono<Void> eliminarBootcamp(Long idBootcamp);
}
