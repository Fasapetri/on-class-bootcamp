package com.onclass.persona.infraestructure.out.repository;

import com.onclass.persona.infraestructure.out.entity.BootcampPersonaEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface IBootcampPersonaRepository extends ReactiveCrudRepository<BootcampPersonaEntity, Long> {

    Flux<BootcampPersonaEntity> findByIdPersona(Long idPersona);
}
