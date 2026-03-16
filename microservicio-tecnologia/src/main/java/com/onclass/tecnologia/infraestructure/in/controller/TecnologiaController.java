package com.onclass.tecnologia.infraestructure.in.controller;

import com.onclass.tecnologia.application.dto.TecnologiaRequest;
import com.onclass.tecnologia.application.handler.TecnologiaHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TecnologiaController {

    private final TecnologiaHandler tecnologiaHandler;

    public Mono<ServerResponse> guardarTecnologia(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(TecnologiaRequest.class)
                .flatMap(tecnologiaHandler::guardarTecnologia)
                .flatMap(response -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }
}
