package com.onclass.capacidad.application.handler;

import com.onclass.capacidad.application.dto.CapacidadRequest;
import com.onclass.capacidad.application.dto.CapacidadResponse;
import com.onclass.capacidad.domain.model.CapacidadDetalle;
import com.onclass.capacidad.domain.model.PaginadoCustom;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ICapacidadHandler {

    Mono<CapacidadResponse> guardarCapacidad(CapacidadRequest request);
    Mono<PaginadoCustom<CapacidadDetalle>> listarCapacidades(int pagina, int tamanio, String ordenarPor, String direccion);
    Mono<Boolean> existenTodasLasCapacidades(List<Long> ids);
    Mono<Void> guardarRelacionBootcampCapacidad(Long idBootcamp, List<Long> capacidades);
}
