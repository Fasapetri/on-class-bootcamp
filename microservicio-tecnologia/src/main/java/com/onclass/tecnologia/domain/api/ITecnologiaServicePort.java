package com.onclass.tecnologia.domain.api;

import com.onclass.tecnologia.domain.model.Tecnologia;
import reactor.core.publisher.Mono;

public interface ITecnologiaServicePort {

    Mono<Tecnologia> guardarTecnologia(Tecnologia tecnologia);

}
