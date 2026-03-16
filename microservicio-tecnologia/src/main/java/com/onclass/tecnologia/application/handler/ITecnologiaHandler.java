package com.onclass.tecnologia.application.handler;

import com.onclass.tecnologia.application.dto.TecnologiaRequest;
import com.onclass.tecnologia.application.dto.TecnologiaResponse;
import reactor.core.publisher.Mono;

public interface ITecnologiaHandler {

    Mono<TecnologiaResponse> guardarTecnologia(TecnologiaRequest tecnologiaRequest);
}
