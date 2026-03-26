package com.onclass.persona.application.handler;

import com.onclass.persona.application.dto.PersonaRequest;
import com.onclass.persona.application.dto.PersonaResponse;
import com.onclass.persona.domain.model.Persona;
import reactor.core.publisher.Mono;

public interface IPersonaHandler {

    Mono<PersonaResponse> guardarPersona(PersonaRequest personaRequest);
}
