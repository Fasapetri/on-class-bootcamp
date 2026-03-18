package com.onclass.tecnologia.domain.spi;

import com.onclass.tecnologia.domain.model.Tecnologia;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ITecnologiaPersistencePort {

    Mono<Tecnologia> guardarTecnologia(Tecnologia tecnologia);
    Mono<Boolean> existeTecnologiaPorNombre(String nombreTecnologia);
    Mono<Boolean> existenTodasLasTecnologias(List<Long> idsTecnologia);
    Mono<Void> guardarRelacionCapacidadTecnologia(Long idCapacidad, List<Long> tecnologias);
}
