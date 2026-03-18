package com.onclass.capacidad.infraestructure.out.repository;

import com.onclass.capacidad.infraestructure.out.entity.CapacidadEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CapacidadRepository extends ReactiveCrudRepository<CapacidadEntity, Long> {
}
