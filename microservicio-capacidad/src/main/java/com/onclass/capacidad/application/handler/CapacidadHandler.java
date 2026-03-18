package com.onclass.capacidad.application.handler;

import com.onclass.capacidad.application.dto.CapacidadRequest;
import com.onclass.capacidad.application.dto.CapacidadResponse;
import com.onclass.capacidad.application.mapper.CapacidadMapper;
import com.onclass.capacidad.domain.api.ICapacidadServicePort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CapacidadHandler implements ICapacidadHandler{

    private final ICapacidadServicePort capacidadServicePort;
    private final CapacidadMapper capacidadMapper;

    public CapacidadHandler(ICapacidadServicePort capacidadServicePort, CapacidadMapper capacidadMapper) {
        this.capacidadServicePort = capacidadServicePort;
        this.capacidadMapper = capacidadMapper;
    }

    @Override
    public Mono<CapacidadResponse> guardarCapacidad(CapacidadRequest request) {
        return Mono.just(request)
                .map(capacidadMapper::toCapacidad)
                .flatMap(capacidadServicePort::guardarCapacidad)
                .map(capacidadMapper::toCapacidadResponse);
    }
}
