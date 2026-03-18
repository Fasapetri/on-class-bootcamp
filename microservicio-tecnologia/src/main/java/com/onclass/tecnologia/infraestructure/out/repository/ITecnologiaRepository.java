package com.onclass.tecnologia.infraestructure.out.repository;

import com.onclass.tecnologia.infraestructure.out.entity.TecnologiaEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ITecnologiaRepository extends ReactiveCrudRepository<TecnologiaEntity, Long> {

    Mono<Boolean> existsByNombre(String nombre);
    Mono<Long> countByIdIn(List<Long> idsTecnologia);
}
