package com.onclass.bootcamp.domain.usecase;

import com.onclass.bootcamp.domain.api.IBootcampServicePort;
import com.onclass.bootcamp.domain.exception.BootcampErrorMessage;
import com.onclass.bootcamp.domain.exception.BootcampException;
import com.onclass.bootcamp.domain.model.Bootcamp;
import com.onclass.bootcamp.domain.spi.IBootcampPersistencePort;
import com.onclass.bootcamp.domain.spi.ICapacidadExternalPort;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BootcampUseCase implements IBootcampServicePort {

    private final IBootcampPersistencePort bootcampPersistencePort;
    private final ICapacidadExternalPort capacidadExternalPort;

    public BootcampUseCase(IBootcampPersistencePort bootcampPersistencePort, ICapacidadExternalPort capacidadExternalPort) {
        this.bootcampPersistencePort = bootcampPersistencePort;
        this.capacidadExternalPort = capacidadExternalPort;
    }

    @Override
    public Mono<Bootcamp> guardarBootcamp(Bootcamp bootcamp) {
        return bootcampPersistencePort.existeBootcampPorNombre(bootcamp.getNombre())
                .flatMap( esValido -> {
                    if(esValido){
                        return Mono.error(new BootcampException(BootcampErrorMessage.BOOTCAMP_DUPLICADO));
                    }
                    return validarCapacidades(bootcamp.getCapacidades())
                            .flatMap(valido -> capacidadExternalPort.existenCapacidades(bootcamp.getCapacidades()))
                            .flatMap(existenTodas -> {
                                if(!existenTodas){
                                    return Mono.error(new BootcampException(BootcampErrorMessage.CAPACIDAD_NO_EXISTE));
                                }
                                return bootcampPersistencePort.guardarBootcamp(bootcamp);
                            });
                }).flatMap(bootcampGuardado ->
                        capacidadExternalPort.guardarRelacionBootcampCapacidad(bootcampGuardado.getId(), bootcampGuardado.getCapacidades())
                                                .thenReturn(bootcampGuardado)
        );

    }

    private Mono<Boolean> validarCapacidades(List<Long> capacidades){

        if(capacidades == null || capacidades.isEmpty()){
            return Mono.error(new BootcampException(BootcampErrorMessage.CAPACIDADES_MINIMAS));
        }
        if(capacidades.size() > 4){
            return Mono.error(new BootcampException(BootcampErrorMessage.CAPACIDADES_MAXIMAS));
        }

        Set<Long> capacidadesNoRepetidas = new HashSet<>(capacidades);
        if(capacidadesNoRepetidas.size() != capacidades.size()){
            return Mono.error(new BootcampException(BootcampErrorMessage.CAPACIDADES_DUPLICADAS));
        }
        return Mono.just(true);
    }
}
