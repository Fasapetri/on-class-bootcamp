package com.onclass.persona.infraestructure.out.repository;

import com.onclass.persona.infraestructure.out.entity.PersonaEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IPersonaRepository extends ReactiveCrudRepository<PersonaEntity, Long> {
}
