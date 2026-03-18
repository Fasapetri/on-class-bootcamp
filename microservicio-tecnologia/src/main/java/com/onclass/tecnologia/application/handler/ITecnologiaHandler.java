package com.onclass.tecnologia.application.handler;

import com.onclass.tecnologia.application.dto.TecnologiaRequest;
import com.onclass.tecnologia.application.dto.TecnologiaResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ITecnologiaHandler {

    Mono<TecnologiaResponse> guardarTecnologia(TecnologiaRequest tecnologiaRequest);
    Mono<Boolean> existenTodasLasTecnologias(List<Long> idsTecnologia);
    Mono<Void> guardarRelacionCapacidadTecnologia(Long idCapacidad, List<Long> tecnologias);
}
