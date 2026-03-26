package com.onclass.bootcamp.application.handler;

import com.onclass.bootcamp.application.dto.BootcampRequest;
import com.onclass.bootcamp.application.dto.BootcampResponse;
import com.onclass.bootcamp.domain.model.BootcampDetalle;
import com.onclass.bootcamp.domain.model.PaginaCustom;
import reactor.core.publisher.Mono;

public interface IBootcampHandler {

    Mono<BootcampResponse> guardarBootcamp(BootcampRequest bootcampRequest);
    Mono<PaginaCustom<BootcampDetalle>> listarBootcamps(int pagina, int tamanio, String orden, String filtro);
}
