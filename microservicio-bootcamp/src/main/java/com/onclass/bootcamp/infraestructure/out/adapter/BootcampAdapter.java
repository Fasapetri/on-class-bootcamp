package com.onclass.bootcamp.infraestructure.out.adapter;

import com.onclass.bootcamp.domain.model.Bootcamp;
import com.onclass.bootcamp.domain.spi.IBootcampPersistencePort;
import com.onclass.bootcamp.infraestructure.out.mapper.BootcampEntityMapper;
import com.onclass.bootcamp.infraestructure.out.repository.IBootcampRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BootcampAdapter implements IBootcampPersistencePort {

    private final IBootcampRepository bootcampRepository;
    private final BootcampEntityMapper bootcampEntityMapper;

    @Override
    public Mono<Bootcamp> guardarBootcamp(Bootcamp bootcamp) {
        return Mono.just(bootcamp)
                .map(bootcampEntityMapper::bootcampToBootcampEntity)
                .flatMap(bootcampRepository::save)
                .map(bootcampEnitityGuardada -> {
                    Bootcamp bootcampDominio = bootcampEntityMapper.bootcampEntityToBootcamp(bootcampEnitityGuardada);
                    bootcampDominio.setCapacidades(bootcamp.getCapacidades());
                    return bootcampDominio;
                });
    }

    @Override
    public Mono<Boolean> existeBootcampPorNombre(String nombreBootcamp) {
        return bootcampRepository.existsByNombre(nombreBootcamp);
    }
}
