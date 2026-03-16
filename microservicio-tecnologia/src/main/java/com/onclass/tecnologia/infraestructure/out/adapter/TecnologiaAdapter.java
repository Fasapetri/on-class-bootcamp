package com.onclass.tecnologia.infraestructure.out.adapter;

import com.onclass.tecnologia.domain.model.Tecnologia;
import com.onclass.tecnologia.domain.spi.ITecnologiaPersistencePort;
import com.onclass.tecnologia.infraestructure.out.mapper.TecnologiaEntityMapper;
import com.onclass.tecnologia.infraestructure.out.repository.ITecnologiaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TecnologiaAdapter implements ITecnologiaPersistencePort {

    private final ITecnologiaRepository tecnologiaRepository;
    private final TecnologiaEntityMapper tecnologiaEntityMapper;

    @Override
    public Mono<Tecnologia> guardarTecnologia(Tecnologia tecnologia) {
        return Mono.just(tecnologia)
                .map(tecnologiaEntityMapper::tecnologiaToTecnologiaEntity)
                .flatMap(tecnologiaRepository::save)
                .map(tecnologiaEntityMapper::tecnologiaEntityToTecnologia);
    }

    @Override
    public Mono<Boolean> existeTecnologiaPorNombre(String nombreTecnologia) {
        return tecnologiaRepository.existsByNombre(nombreTecnologia);
    }
}
