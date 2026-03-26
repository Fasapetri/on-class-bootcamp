package com.onclass.capacidad.infraestructure.out.repository;

import com.onclass.capacidad.infraestructure.out.entity.BootcampCapacidadEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IBootcampCapacidadRepository extends ReactiveCrudRepository<BootcampCapacidadEntity, Long> {
}
