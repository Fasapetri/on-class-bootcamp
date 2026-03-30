package com.onclass.reporte.infraestructure.in.controller;

import com.onclass.reporte.application.dto.ReporteBootcampRequest;
import com.onclass.reporte.application.dto.ReporteBootcampResponse;
import com.onclass.reporte.application.handler.IReporteBootcampHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ReporteBootcampController {

    private final IReporteBootcampHandler reporteBootcampHandler;

    public Mono<ServerResponse> guardarReporte(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(ReporteBootcampRequest.class)
                .flatMap(reporteBootcampHandler::guardarReporte)
                .then(ServerResponse.status(HttpStatus.CREATED).build());
    }

    public Mono<ServerResponse> registrarNuevaInscripcion(ServerRequest serverRequest) {
        Long idBootcamp = Long.valueOf(serverRequest.pathVariable("idBootcamp"));
        return serverRequest.bodyToMono(new ParameterizedTypeReference<Map<String, String>>() {})
                .flatMap(bodyMap -> {
                    String nombrePersona = bodyMap.get("nombre");
                    String correoPersona = bodyMap.get("correo");
                    return reporteBootcampHandler.registrarNuevaInscripcion(idBootcamp, nombrePersona, correoPersona);
                })
                .then(ServerResponse.ok().build());
    }

    public Mono<ServerResponse> obtenerBootcampMasInscritos(ServerRequest serverRequest) {
        return reporteBootcampHandler.obtenerBootcampMasInscritos()
                .flatMap(reporte -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(reporte))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> listarReporteBootcamps(ServerRequest serverRequest){
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(reporteBootcampHandler.listarReporteBootcamp(), ReporteBootcampResponse.class);

    }
}
