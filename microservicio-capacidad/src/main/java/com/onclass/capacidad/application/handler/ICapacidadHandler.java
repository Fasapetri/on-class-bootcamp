package com.onclass.capacidad.application.handler;

import com.onclass.capacidad.application.dto.CapacidadRequest;
import com.onclass.capacidad.application.dto.CapacidadResponse;
import reactor.core.publisher.Mono;

public interface ICapacidadHandler {

    Mono<CapacidadResponse> guardarCapacidad(CapacidadRequest request);
}
