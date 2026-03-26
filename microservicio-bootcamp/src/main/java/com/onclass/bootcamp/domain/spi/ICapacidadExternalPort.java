package com.onclass.bootcamp.domain.spi;

import com.onclass.bootcamp.domain.model.Capacidad;
import com.onclass.bootcamp.domain.model.PaginaCustom;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface ICapacidadExternalPort {

    Mono<Boolean> guardarRelacionBootcampCapacidad(Long idBootcamp, List<Long> capacidades);
    Mono<Boolean> existenCapacidades(List<Long> capacidades);
    Mono<PaginaCustom<Long>> obtenerIdsBootcampsOrdenadosPorCantidad(int numeroPagina, int tamanioPagina, String orden);
    Mono<Map<Long, List<Capacidad>>> obtenerCapacidadesPorBootcamp(List<Long> bootcampsIds);
    Mono<Void> eliminarRelacionesYCapacidadesHuerfanas(Long idBootcamp);
}
