package com.onclass.persona.domain.spi;

import com.onclass.persona.domain.model.Persona;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IPersonaPersistencePort {

    Mono<Persona> guardarPersona(Persona persona);
    Mono<Void> guardarInscripcion(Long idPersona, List<Long> idsBootcamps);
    Flux<Long> buscarIdsBootcampsPorPersona(Long idPersona);
    Mono<Boolean> existePersona(Long idPersona);
    Mono<Persona> buscarPersona(Long idPersona);
}
