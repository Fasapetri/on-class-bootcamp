package com.onclass.capacidad.domain.api;

import com.onclass.capacidad.domain.model.Capacidad;
import com.onclass.capacidad.domain.model.CapacidadDetalle;
import com.onclass.capacidad.domain.model.PaginadoCustom;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface ICapacidadServicePort {

    Mono<Capacidad> guardarCapacidad(Capacidad capacidad);
    Mono<PaginadoCustom<CapacidadDetalle>> listarCapacidades(int pagina, int tamanio, String ordenarPor, String direccion);
    Mono<Boolean> existenTodasLasCapacidades(List<Long> ids);
    Mono<Void> guardarRelacionBootcampCapacidad(Long idBootcamp, List<Long> capacidades);
    Mono<PaginadoCustom<Long>> obtenerIdsBootcampsOrdenadosPorCantidad(int pagina, int tamanio, String filtro);
    Mono<Map<Long, List<CapacidadDetalle>>> obtenerCapacidadesPorBootcamps(List<Long> idsBootcamp);
}
