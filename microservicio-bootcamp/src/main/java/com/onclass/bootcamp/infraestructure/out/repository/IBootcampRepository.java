package com.onclass.bootcamp.infraestructure.out.repository;

import com.onclass.bootcamp.infraestructure.out.entity.BootcampEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBootcampRepository extends ReactiveCrudRepository<BootcampEntity, Long> {

    Mono<Boolean> existsByNombre(String nombreBootcamp);
    Flux<BootcampEntity> findAllBy(Pageable pageable);
}
