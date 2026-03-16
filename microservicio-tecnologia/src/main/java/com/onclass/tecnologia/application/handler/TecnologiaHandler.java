package com.onclass.tecnologia.application.handler;

import com.onclass.tecnologia.application.dto.TecnologiaRequest;
import com.onclass.tecnologia.application.dto.TecnologiaResponse;
import com.onclass.tecnologia.application.mapper.TecnologiaMapper;
import com.onclass.tecnologia.domain.api.ITecnologiaServicePort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TecnologiaHandler implements ITecnologiaHandler{

    private final ITecnologiaServicePort tecnologiaServicePort;
    private final TecnologiaMapper tecnologiaMapper;

    public TecnologiaHandler(ITecnologiaServicePort tecnologiaServicePort, TecnologiaMapper tecnologiaMapper) {
        this.tecnologiaServicePort = tecnologiaServicePort;
        this.tecnologiaMapper = tecnologiaMapper;
    }


    @Override
    public Mono<TecnologiaResponse> guardarTecnologia(TecnologiaRequest tecnologiaRequest) {

        return Mono.just(tecnologiaRequest)
                .map(tecnologiaMapper::tecnologiaRequestToTecnologia)
                .flatMap(tecnologiaServicePort::guardarTecnologia)
                .map(tecnologiaMapper::tecnologiaToTecnologiaResponse);
    }
}
