package com.onclass.tecnologia.domain.spi;

import com.onclass.tecnologia.domain.model.Tecnologia;
import reactor.core.publisher.Mono;

public interface ITecnologiaPersistencePort {

    Mono<Tecnologia> guardarTecnologia(Tecnologia tecnologia);
    Mono<Boolean> existeTecnologiaPorNombre(String nombreTecnologia);
}
