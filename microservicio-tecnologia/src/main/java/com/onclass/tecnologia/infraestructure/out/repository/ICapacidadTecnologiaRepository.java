package com.onclass.tecnologia.infraestructure.out.repository;

import com.onclass.tecnologia.infraestructure.out.entity.CapacidadTecnologiaEntity;
import com.onclass.tecnologia.infraestructure.out.entity.CapacidadTecnologiaProjection;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ICapacidadTecnologiaRepository extends ReactiveCrudRepository<CapacidadTecnologiaEntity, Long> {

    @Query(
            "SELECT ct.id_capacidad, ct.id_tecnologia, t.nombre as nombre_tecnologia " +
            "FROM capacidad_tecnologia ct " +
            "JOIN tecnologia t ON ct.id_tecnologia = t.id " +
            "WHERE ct.id_capacidad IN (:capacidadIds)"
    )
    Flux<CapacidadTecnologiaProjection> findTecnologiasByCapacidadIds(List<Long> capacidadIds);

    @Query(
            "SELECT ct.id_capacidad " +
            "FROM capacidad_tecnologia ct " +
            "GROUP BY ct.id_capacidad " +
            "ORDER BY COUNT(id_tecnologia) DESC " +
            "LIMIT :limit OFFSET :offset"
    )
    Flux<Long> findCapacidadIdsOrderedByCountDesc(int limit, int offset);

    @Query(
            "SELECT ct.id_capacidad " +
            "FROM capacidad_tecnologia ct " +
            "GROUP BY ct.id_capacidad " +
            "ORDER BY COUNT(id_tecnologia) ASC " +
            "LIMIT :limit OFFSET :offset"
    )
    Flux<Long> findCapacidadIdsOrderedByCountAsc(int limit, int offset);

    @Query(
            "SELECT COUNT(DISTINCT id_capacidad) FROM capacidad_tecnologia"
    )
    Mono<Long> countDistinctCapacidades();
}
