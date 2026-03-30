package com.onclass.bootcamp.domain.spi;

import com.onclass.bootcamp.domain.model.ReporteBootcamp;
import reactor.core.publisher.Mono;

public interface IReporteBootcampExternalPort {

    Mono<Void> enviarReporteNuevoBootcamp(ReporteBootcamp reporteBootcamp);
}
