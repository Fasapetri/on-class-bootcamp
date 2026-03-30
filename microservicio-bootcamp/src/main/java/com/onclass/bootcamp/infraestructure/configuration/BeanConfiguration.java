package com.onclass.bootcamp.infraestructure.configuration;

import com.onclass.bootcamp.domain.api.IBootcampServicePort;
import com.onclass.bootcamp.domain.spi.IBootcampPersistencePort;
import com.onclass.bootcamp.domain.spi.ICapacidadExternalPort;
import com.onclass.bootcamp.domain.spi.IReporteBootcampExternalPort;
import com.onclass.bootcamp.domain.usecase.BootcampUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BeanConfiguration {

    @Bean
    public IBootcampServicePort bootcampServicePort(IBootcampPersistencePort bootcampPersistencePort, ICapacidadExternalPort capacidadExternalPort, IReporteBootcampExternalPort reporteBootcampExternalPort) {
        return new BootcampUseCase(bootcampPersistencePort, capacidadExternalPort, reporteBootcampExternalPort);
    }

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
