package com.onclass.capacidad.domain.spi;

import com.onclass.capacidad.domain.model.BootcampCapacidadProjection;
import com.onclass.capacidad.domain.model.Capacidad;
import com.onclass.capacidad.domain.model.PaginadoCustom;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ICapacidadPersistencePort {

    Mono<Capacidad> guardarCapacidad(Capacidad capacidad);
    Mono<PaginadoCustom<Capacidad>> listarCapacidadesPaginadas(int pagina, int tamanio, String direccionAscDesc);
    Flux<Capacidad> obtenerCapacidadesPorIds(List<Long> ids);
    Mono<Boolean> existenTodasLasCapacidades(List<Long> ids);
    Mono<Void> guardarRelacionBootcampCapacidad(Long idBootcamp, List<Long> capacidades);
    Mono<PaginadoCustom<Long>> obtenerIdsBootcampsOrdenadosPorCantidad(int pagina, int tamanio, String filtro);
    Flux<BootcampCapacidadProjection> obtenerProyeccionesPorBootcamps(List<Long> idsBootcamp);
    Flux<Long> findCapacidadesHuerfanas(List<Long> idsCapacidades);
    Mono<Void> eliminarRelacionesYCapacidadesHuerfanas(Long idBootcamp, List<Long> idsCapacidades);
    Flux<Long> findCapacidadesPorBootcamp(Long idBootcamp);
}
