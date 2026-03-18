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

    public Mono<ServerResponse> listarCapacidadesPaginadas(ServerRequest request) {
        int pagina = request.queryParam("page").map(Integer::parseInt).orElse(0);
        int tamanio = request.queryParam("size").map(Integer::parseInt).orElse(10);
        String direccion = request.queryParam("direction").orElse("asc");
        String orden = request.queryParam("sort").orElse("nombre");

        return capacidadHandler.listarCapacidades(pagina, tamanio, orden, direccion)
                .flatMap(paginaCustom -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(paginaCustom));

    }
}
