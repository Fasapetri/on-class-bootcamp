package com.onclass.capacidad.infraestructure.in.controller;

import com.onclass.capacidad.application.dto.CapacidadRequest;
import com.onclass.capacidad.application.dto.RelacionBootcampCapacidadRequest;
import com.onclass.capacidad.application.handler.CapacidadHandler;
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

    public Mono<ServerResponse> validarCapacidades(ServerRequest request){
        return request.bodyToMono(new ParameterizedTypeReference<List<Long>>() {})
                .flatMap(capacidadHandler::existenTodasLasCapacidades)
                .flatMap(existen -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(existen));
    }

    public Mono<ServerResponse> guardarRelacionBootcampCapacidad(ServerRequest request){
        return request.bodyToMono(RelacionBootcampCapacidadRequest.class)
                .flatMap(req -> capacidadHandler.guardarRelacionBootcampCapacidad(req.idBootcamp(), req.capacidades()))
                .flatMap(capacidadResponse ->
                        ServerResponse.status(HttpStatus.CREATED)
                                .build());
    }

    public Mono<ServerResponse> obtenerIdsBootcampsOrdenados(ServerRequest serverRequest){
        int pagina = serverRequest.queryParam("page").map(Integer::parseInt).orElse(0);
        int tamanio = serverRequest.queryParam("size").map(Integer::parseInt).orElse(10);
        String filtro = serverRequest.queryParam("filtro").orElse("desc");

        return capacidadHandler.obtenerIdsBootcampsOrdenados(pagina, tamanio, filtro)
                .flatMap(paginaIds-> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(paginaIds));
    }

    public Mono<ServerResponse> obtenerCapacidadesPorBootcamps(ServerRequest serverRequest){
        return serverRequest.bodyToMono(new ParameterizedTypeReference<List<Long>>() {})
                .flatMap(capacidadHandler::obtenerCapacidadesPorBootcamps)
                .flatMap(capacidadResponse ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(capacidadResponse));
    }
}
