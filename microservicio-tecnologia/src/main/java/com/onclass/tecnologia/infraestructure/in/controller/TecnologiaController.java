package com.onclass.tecnologia.infraestructure.in.controller;

import com.onclass.tecnologia.application.dto.RelacionCapacidadTecnologiaRequest;
import com.onclass.tecnologia.application.dto.TecnologiaRequest;
import com.onclass.tecnologia.application.handler.TecnologiaHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

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

    public Mono<ServerResponse> existenTodasLasTecnologias(ServerRequest request){
        return request.bodyToMono(new ParameterizedTypeReference<List<Long>>() {})
                .flatMap(tecnologiaHandler::existenTodasLasTecnologias)
                .flatMap(exist -> ServerResponse
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(exist));
    }

    public Mono<ServerResponse> guardarRelacionCapacidadTecnologia(ServerRequest request){
        return request.bodyToMono(RelacionCapacidadTecnologiaRequest.class)
                .flatMap(req -> tecnologiaHandler.guardarRelacionCapacidadTecnologia(req.idCapacidad(), req.tecnologias()))
                .flatMap(response -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .build());
    }
}
