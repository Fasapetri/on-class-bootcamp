package com.onclass.persona.domain.spi;

import reactor.core.publisher.Mono;

public interface IReporteBootcampExternalPort {

    Mono<Void> notificarInscripcionAReporteBootcamp(Long idBootcamp, String nombrePersona, String correoPersona);
}
