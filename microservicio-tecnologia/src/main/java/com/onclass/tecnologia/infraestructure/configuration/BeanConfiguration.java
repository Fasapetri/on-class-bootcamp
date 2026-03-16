package com.onclass.tecnologia.infraestructure.configuration;

import com.onclass.tecnologia.domain.api.ITecnologiaServicePort;
import com.onclass.tecnologia.domain.spi.ITecnologiaPersistencePort;
import com.onclass.tecnologia.domain.usecase.TecnologiaUseCase;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    public ITecnologiaServicePort tecnologiaServicePort(ITecnologiaPersistencePort tecnologiaPersistencePort) {
        return new TecnologiaUseCase(tecnologiaPersistencePort);
    }
}
