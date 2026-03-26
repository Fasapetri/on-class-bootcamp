package com.onclass.bootcamp.infraestructure.in.controller;

import com.onclass.bootcamp.application.dto.BootcampRequest;
import com.onclass.bootcamp.application.handler.IBootcampHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BootcampController {

    private final IBootcampHandler bootcampHandler;

    public Mono<ServerResponse> guardarBootcamp(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(BootcampRequest.class)
                .flatMap(bootcampHandler::guardarBootcamp)
                .flatMap(bootcampResponse ->
                        ServerResponse.status(HttpStatus.CREATED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(bootcampResponse));
    }
}
