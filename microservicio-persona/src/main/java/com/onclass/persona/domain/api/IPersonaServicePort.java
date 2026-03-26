package com.onclass.persona.domain.api;

import com.onclass.persona.domain.model.Persona;
import reactor.core.publisher.Mono;

public interface IPersonaServicePort {

    Mono<Persona> guardarPersona(Persona persona);
    Mono<Void> inscribirPersonaBootcamp(Long personaId, Long bootcampId);
}
