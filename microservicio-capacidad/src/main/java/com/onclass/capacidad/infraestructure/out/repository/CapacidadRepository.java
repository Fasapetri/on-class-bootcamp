package com.onclass.capacidad.infraestructure.out.repository;

import com.onclass.capacidad.infraestructure.out.entity.CapacidadEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


public interface CapacidadRepository extends ReactiveCrudRepository<CapacidadEntity, Long> {

    Flux<CapacidadEntity> findAllBy(Pageable pageable);
    Mono<Long> countByIdIn(List<Long> ids);
    Mono<Void> deleteByIdIn(List<Long> ids);
}
