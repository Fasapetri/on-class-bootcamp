package com.onclass.reporte.domain.spi;

import com.onclass.reporte.domain.model.ReporteBootcamp;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IReporteBootcampPersistencePort {

    Mono<Void> guardarReporte(ReporteBootcamp reporteBootcamp);
    Mono<ReporteBootcamp> buscarReportePorIdBootcamp(Long idBootcamp);
    Mono<ReporteBootcamp> buscarBootcampMasInscritos();
    Flux<ReporteBootcamp> listarReporteBootcamp();
}
