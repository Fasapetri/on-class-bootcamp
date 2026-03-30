package com.onclass.persona.infraestructure.configuration;

import com.onclass.persona.domain.api.IPersonaServicePort;
import com.onclass.persona.domain.spi.IBootcampExternalPort;
import com.onclass.persona.domain.spi.IPersonaPersistencePort;
import com.onclass.persona.domain.spi.IReporteBootcampExternalPort;
import com.onclass.persona.domain.usecase.PersonaUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BeanConfiguration {

    @Bean
    public IPersonaServicePort personaServicePort(IPersonaPersistencePort personaPersistencePort, IBootcampExternalPort bootcampExternalPort, IReporteBootcampExternalPort reporteBootcampExternalPort) {
        return new PersonaUseCase(personaPersistencePort, bootcampExternalPort, reporteBootcampExternalPort);
    }

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
