package com.onclass.persona.domain.api;

import com.onclass.persona.domain.model.Inscripcion;
import com.onclass.persona.domain.model.Persona;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IPersonaServicePort {

    Mono<Persona> guardarPersona(Persona persona);
    Mono<Void> inscribirPersonaBootcamp(Inscripcion inscripcion);
}
