package com.onclass.capacidad.infraestructure.configuration;

import com.onclass.capacidad.domain.api.ICapacidadServicePort;
import com.onclass.capacidad.domain.spi.ICapacidadPersistencePort;
import com.onclass.capacidad.domain.spi.ITecnologiaExternalPort;
import com.onclass.capacidad.domain.usecase.CapacidadUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public ICapacidadServicePort capacidadServicePort(ICapacidadPersistencePort capacidadPersistencePort, ITecnologiaExternalPort tecnologiaExternalPort){
        return new CapacidadUseCase(capacidadPersistencePort, tecnologiaExternalPort);
    }


}
