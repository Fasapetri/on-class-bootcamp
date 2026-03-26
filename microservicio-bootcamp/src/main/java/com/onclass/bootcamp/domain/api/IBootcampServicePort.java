package com.onclass.bootcamp.domain.api;

import com.onclass.bootcamp.domain.model.Bootcamp;
import com.onclass.bootcamp.domain.model.BootcampDetalle;
import com.onclass.bootcamp.domain.model.PaginaCustom;
import reactor.core.publisher.Mono;

public interface IBootcampServicePort {

    Mono<Bootcamp> guardarBootcamp(Bootcamp bootcamp);
    Mono<PaginaCustom<BootcampDetalle>> buscarBootcamps(int numeroPagina, int tamanioPagina, String orden, String filtro);
}
