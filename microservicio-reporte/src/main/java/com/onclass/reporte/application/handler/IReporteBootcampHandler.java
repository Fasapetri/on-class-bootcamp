package com.onclass.reporte.application.handler;

import com.onclass.reporte.application.dto.ReporteBootcampRequest;
import com.onclass.reporte.application.dto.ReporteBootcampResponse;
import com.onclass.reporte.domain.model.ReporteBootcamp;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IReporteBootcampHandler {

    Mono<Void> guardarReporte(ReporteBootcampRequest reporteBootcampRequest);
    Mono<Void> registrarNuevaInscripcion(Long idBootcamp, String nombrePersona, String correoPersona);
    Mono<ReporteBootcamp> obtenerBootcampMasInscritos();
    Flux<ReporteBootcampResponse> listarReporteBootcamp();
}
