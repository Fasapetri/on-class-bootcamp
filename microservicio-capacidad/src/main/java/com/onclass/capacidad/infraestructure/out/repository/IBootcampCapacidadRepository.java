package com.onclass.capacidad.infraestructure.out.repository;

import com.onclass.capacidad.infraestructure.out.entity.BootcampCapacidadEntity;
import com.onclass.capacidad.infraestructure.out.entity.BootcampCapacidadProjectionEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IBootcampCapacidadRepository extends ReactiveCrudRepository<BootcampCapacidadEntity, Long> {

    @Query("SELECT bc.id_bootcamp as id_bootcamp, c.id as id_capacidad, c.nombre as nombre_capacidad " +
            "FROM bootcamp_capacidad bc " +
            "JOIN capacidad c ON bc.id_capacidad = c.id " +
            "WHERE bc.id_bootcamp IN (:idsBootcamp)")
    Flux<BootcampCapacidadProjectionEntity> findCapacidadesByBootcampId(List<Long> idsBootcamp);

    @Query("SELECT id_bootcamp FROM bootcamp_capacidad GROUP BY id_bootcamp ORDER BY COUNT(id_capacidad) DESC LIMIT :limit OFFSET :offset")
    Flux<Long> findBootcampIdsOrderedByCountDesc(int limit, int offset);

    @Query("SELECT id_bootcamp FROM bootcamp_capacidad GROUP BY id_bootcamp ORDER BY COUNT(id_capacidad) ASC LIMIT :limit OFFSET :offset")
    Flux<Long> findBootcampIdsOrderedByCountAsc(int limit, int offset);

    @Query("SELECT COUNT(DISTINCT id_bootcamp) FROM bootcamp_capacidad")
    Mono<Long> countDistinctBootcamps();

    @Query("SELECT id_capacidad FROM bootcamp_capacidad WHERE id_capacidad IN (:idsCapacidades) GROUP BY id_capacidad HAVING COUNT(id_bootcamp) = 1")
    Flux<Long> findCapacidadesHuerfanas(List<Long> idsCapacidades);

    Flux<BootcampCapacidadEntity> findByIdBootcamp(Long idBootcamp);

    Mono<Void> deleteByIdBootcamp(Long idBootcamp);
}
