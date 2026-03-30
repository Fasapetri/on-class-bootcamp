package com.onclass.reporte.domain.api;

import com.onclass.reporte.domain.model.ReporteBootcamp;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IReporteBootcampServicePort {

    Mono<Void> guardarReporte(ReporteBootcamp reporteBootcamp);
    Mono<Void> registrarNuevaInscripcion(Long idBootcamp, String nombrePersona, String correoPersona);
    Mono<ReporteBootcamp> obtenerBootcampMasInscritos();
    Flux<ReporteBootcamp> listarReporteBootcamp();
}
