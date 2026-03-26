package com.onclass.persona.domain.spi;

import com.onclass.persona.domain.model.Persona;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPersonaPersistencePort {

    Mono<Persona> guardarPersona(Persona persona);
    Mono<Void> guardarInscripcion(Long idPersona, Long idBootcamp);
    Flux<Long> buscarIdsBootcampsPorPersona(Long idPersona);
}
