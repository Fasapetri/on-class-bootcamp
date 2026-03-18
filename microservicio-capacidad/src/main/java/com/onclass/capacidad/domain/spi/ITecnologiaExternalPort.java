package com.onclass.capacidad.domain.spi;

import com.onclass.capacidad.domain.model.PaginadoCustom;
import com.onclass.capacidad.domain.model.Tecnologia;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface ITecnologiaExternalPort {

    Mono<Boolean> guardarRelacionCapacidadTecnologia(Long idCapacidad, List<Long> tecnologias);
    Mono<Boolean> existenTecnologias(List<Long> tecnologias);
    Mono<Map<Long, List<Tecnologia>>> obtenerTecnologiasPorCapacidad(List<Long> idsCapacidades);
    Mono<PaginadoCustom<Long>> obtenerIdsCapacidadesOrdenadosPorCantidad(int pagina, int tamanio, String direccionAscDesc);
}
