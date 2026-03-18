package com.onclass.capacidad.domain.usecase;

import com.onclass.capacidad.domain.api.ICapacidadServicePort;
import com.onclass.capacidad.domain.exception.CapacidadErrorMessage;
import com.onclass.capacidad.domain.exception.CapacidadException;
import com.onclass.capacidad.domain.model.Capacidad;
import com.onclass.capacidad.domain.spi.ICapacidadPersistencePort;
import com.onclass.capacidad.domain.spi.ITecnologiaExternalPort;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CapacidadUseCase implements ICapacidadServicePort {

    private final ICapacidadPersistencePort capacidadPersistencePort;
    private final ITecnologiaExternalPort tecnologiaExternalPort;

    public CapacidadUseCase(ICapacidadPersistencePort capacidadPersistencePort, ITecnologiaExternalPort tecnologiaExternalPort) {
        this.capacidadPersistencePort = capacidadPersistencePort;
        this.tecnologiaExternalPort = tecnologiaExternalPort;
    }

    @Override
    public Mono<Capacidad> guardarCapacidad(Capacidad capacidad) {

        return validarTecnologia(capacidad.getTecnologias())
                .flatMap(valido -> tecnologiaExternalPort.existenTecnologias(capacidad.getTecnologias()))
                .flatMap(existenTecnologias -> {
                    if(!existenTecnologias){
                        return Mono.error(new CapacidadException(CapacidadErrorMessage.TECNOLOGIA_NO_EXISTE));
                    }
                    return capacidadPersistencePort.guardarCapacidad(capacidad);
                })
                .flatMap(capacidadGuardada ->
                    tecnologiaExternalPort.guardarRelacionCapacidadTecnologia(capacidadGuardada.getId(), capacidadGuardada.getTecnologias())
                            .thenReturn(capacidadGuardada)
                );
    }

    private Mono<Boolean> validarTecnologia(List<Long> tecnologias){
        if(tecnologias == null || tecnologias.size() < 3){
            return Mono.error(new CapacidadException(CapacidadErrorMessage.TECNOLOGIAS_MINIMAS));
        }
        if(tecnologias.size() > 20){
            return Mono.error(new CapacidadException(CapacidadErrorMessage.TECNOLOGIAS_MAXIMAS));
        }

        Set<Long> tecnologiasNoDuplicadas = new HashSet<>(tecnologias);
        if(tecnologiasNoDuplicadas.size() != tecnologias.size()){
            return Mono.error(new CapacidadException(CapacidadErrorMessage.TECNOLOGIAS_DUPLICADAS));
        }
        return Mono.just(true);
    }
}
