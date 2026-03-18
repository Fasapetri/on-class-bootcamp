package com.onclass.tecnologia.domain.usecase;

import com.onclass.tecnologia.domain.api.ITecnologiaServicePort;
import com.onclass.tecnologia.domain.exception.TecnologiaErrorMessage;
import com.onclass.tecnologia.domain.exception.TecnologiaException;
import com.onclass.tecnologia.domain.model.Tecnologia;
import com.onclass.tecnologia.domain.spi.ITecnologiaPersistencePort;
import reactor.core.publisher.Mono;

import java.util.List;

public class TecnologiaUseCase implements ITecnologiaServicePort {

    private final ITecnologiaPersistencePort tecnologiaPersistencePort;

    public TecnologiaUseCase(ITecnologiaPersistencePort tecnologiaPersistencePort) {
        this.tecnologiaPersistencePort = tecnologiaPersistencePort;
    }

    @Override
    public Mono<Tecnologia> guardarTecnologia(Tecnologia tecnologia) {

        return validarTecnologia(tecnologia)
                .flatMap(esValido -> tecnologiaPersistencePort.existeTecnologiaPorNombre(tecnologia.getNombre()))
                .flatMap(existe -> {
                    if (existe) {
                        return Mono.error(new TecnologiaException(TecnologiaErrorMessage.TECNOLOGIA_DUPLICADA));
                    }
                    return tecnologiaPersistencePort.guardarTecnologia(tecnologia);
                });

    }

    @Override
    public Mono<Boolean> existenTodasLasTecnologias(List<Long> idsTecnologia) {
        return tecnologiaPersistencePort.existenTodasLasTecnologias(idsTecnologia);
    }

    @Override
    public Mono<Void> guardarRelacionCapacidadTecnologia(Long idCapacidad, List<Long> tecnologias) {
        return tecnologiaPersistencePort.guardarRelacionCapacidadTecnologia(idCapacidad, tecnologias);
    }

    private Mono<Boolean> validarTecnologia(Tecnologia tecnologia) {
        if (tecnologia.getNombre() == null || tecnologia.getNombre().isBlank()) {
            return Mono.error(new TecnologiaException(TecnologiaErrorMessage.NOMBRE_REQUERIDO));
        }
        if(tecnologia.getNombre().length() > 50){
            return Mono.error(new TecnologiaException(TecnologiaErrorMessage.NOMBRE_EXCEDE_LONGITUD));
        }
        if (tecnologia.getDescripcion() == null || tecnologia.getDescripcion().isBlank()) {
            return Mono.error(new TecnologiaException(TecnologiaErrorMessage.DESCRIPCION_REQUERIDA));
        }
        if(tecnologia.getDescripcion().length() > 90){
            return Mono.error(new TecnologiaException(TecnologiaErrorMessage.DESCRIPCION_EXCEDE_LONGITUD));
        }
        return Mono.just(true);
    }
}
