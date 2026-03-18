package com.onclass.capacidad.infraestructure.in.controller;

import com.onclass.capacidad.application.dto.CapacidadRequest;
import com.onclass.capacidad.application.handler.CapacidadHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CapacidadController {

    private final CapacidadHandler capacidadHandler;

    public Mono<ServerResponse> guardarCapacidad(ServerRequest request) {
        return request.bodyToMono(CapacidadRequest.class)
                .flatMap(capacidadHandler::guardarCapacidad)
                .flatMap(capacidadResponse ->
                        ServerResponse.status(HttpStatus.CREATED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(capacidadResponse));
    }
}
