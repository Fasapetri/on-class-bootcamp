package com.onclass.bootcamp.infraestructure.in.controller;

import com.onclass.bootcamp.application.dto.BootcampFechaResponse;
import com.onclass.bootcamp.application.dto.BootcampRequest;
import com.onclass.bootcamp.application.handler.BootcampHandler;
import com.onclass.bootcamp.application.handler.IBootcampHandler;
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
public class BootcampController {

    private final BootcampHandler bootcampHandler;

    public Mono<ServerResponse> guardarBootcamp(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(BootcampRequest.class)
                .flatMap(bootcampHandler::guardarBootcamp)
                .flatMap(bootcampResponse ->
                        ServerResponse.status(HttpStatus.CREATED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(bootcampResponse));
    }

    public Mono<ServerResponse> listarBootcamps(ServerRequest serverRequest){
        int pagina = serverRequest.queryParam("page").map(Integer::parseInt).orElse(0);
        int tamanio = serverRequest.queryParam("size").map(Integer::parseInt).orElse(10);
        String orden = serverRequest.queryParam("sort").orElse("nombre");
        String filtro = serverRequest.queryParam("filtro").orElse("asc");

        return bootcampHandler.listarBootcamps(pagina, tamanio, orden, filtro)
                .flatMap(paginaCustom ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(paginaCustom));
    }

    public Mono<ServerResponse> eliminarBootcamp(ServerRequest serverRequest) {
        Long idBootcamp = Long.valueOf(serverRequest.pathVariable("id"));
        return bootcampHandler.eliminarBootcamp(idBootcamp)
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> obtenerFechasBootcamps(ServerRequest serverRequest){
        return serverRequest.bodyToMono(new ParameterizedTypeReference<List<Long>>() {})
                .flatMapMany(bootcampHandler::buscarBootcampsPorIds)
                .map(bootcamp -> new BootcampFechaResponse(bootcamp.getId(), bootcamp.getFechaLanzamiento(), bootcamp.getDuracion()))
                .collectList()
                .flatMap(bootcampFechaResponses ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(bootcampFechaResponses));
    }
}
