package com.onclass.capacidad.infraestructure.out.adapter;

import com.onclass.capacidad.domain.model.Capacidad;
import com.onclass.capacidad.domain.spi.ICapacidadPersistencePort;
import com.onclass.capacidad.infraestructure.out.mapper.CapacidadEntityMapper;
import com.onclass.capacidad.infraestructure.out.repository.CapacidadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CapacidadAdapter implements ICapacidadPersistencePort {

    private final CapacidadRepository capacidadRepository;
    private final CapacidadEntityMapper capacidadEntityMapper;

    @Override
    public Mono<Capacidad> guardarCapacidad(Capacidad capacidad) {
        return Mono.just(capacidad)
                .map(capacidadEntityMapper::toCapacidadEntity)
                .flatMap(capacidadRepository::save)
                .map(capacidadEntity -> {
                    Capacidad capacidadDominio = capacidadEntityMapper.toCapacidad(capacidadEntity);
                    capacidadDominio.setTecnologias(capacidad.getTecnologias());
                    return capacidadDominio;
                });
    }
}
