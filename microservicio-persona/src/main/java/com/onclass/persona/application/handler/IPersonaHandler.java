package com.onclass.persona.application.handler;

import com.onclass.persona.application.dto.InscripcionRequest;
import com.onclass.persona.application.dto.PersonaRequest;
import com.onclass.persona.application.dto.PersonaResponse;
import reactor.core.publisher.Mono;


public interface IPersonaHandler {

    Mono<PersonaResponse> guardarPersona(PersonaRequest personaRequest);
    Mono<Void> inscribirPersonaBootcamp(InscripcionRequest inscripcionRequest);
}
