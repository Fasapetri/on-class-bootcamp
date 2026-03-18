package com.onclass.tecnologia.application.handler;

import com.onclass.tecnologia.application.dto.TecnologiaRequest;
import com.onclass.tecnologia.application.dto.TecnologiaResponse;
import com.onclass.tecnologia.application.mapper.TecnologiaMapper;
import com.onclass.tecnologia.domain.api.ITecnologiaServicePort;
import com.onclass.tecnologia.domain.model.PaginaCustom;
import com.onclass.tecnologia.domain.model.Tecnologia;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

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

    @Override
    public Mono<Boolean> existenTodasLasTecnologias(List<Long> idsTecnologia) {
        return tecnologiaServicePort.existenTodasLasTecnologias(idsTecnologia);
    }

    @Override
    public Mono<Void> guardarRelacionCapacidadTecnologia(Long idCapacidad, List<Long> tecnologias) {
        return tecnologiaServicePort.guardarRelacionCapacidadTecnologia(idCapacidad, tecnologias);
    }

    @Override
    public Mono<Map<Long, List<Tecnologia>>> obtenerTecnologiasPorCapacidades(List<Long> idsCapacidad) {
        return tecnologiaServicePort.obtenerTecnologiasPorCapacidades(idsCapacidad);
    }

    @Override
    public Mono<PaginaCustom<Long>> obtenerCapacidadesOrdenadasPorCantidad(int pagina, int tamanio, String direccion) {
        return tecnologiaServicePort.obtenerCapacidadesOrdenadasPorCantidad(pagina, tamanio, direccion);
    }
}
