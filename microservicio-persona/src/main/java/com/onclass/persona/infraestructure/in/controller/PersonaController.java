package com.onclass.persona.infraestructure.in.controller;

import com.onclass.persona.application.dto.PersonaRequest;
import com.onclass.persona.application.handler.PersonaHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PersonaController {

    private final PersonaHandler personaHandler;

    public Mono<ServerResponse> guardarPersona(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(PersonaRequest.class)
                .flatMap(personaHandler::guardarPersona)
                .flatMap(personaGuardada -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(personaGuardada));
    }
}
