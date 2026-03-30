package com.onclass.reporte.infraestructure.configuration;

import com.onclass.reporte.domain.api.IReporteBootcampServicePort;
import com.onclass.reporte.domain.spi.IReporteBootcampPersistencePort;
import com.onclass.reporte.domain.usecase.ReporteBootcampUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public IReporteBootcampServicePort reporteBootcampServicePort(IReporteBootcampPersistencePort reporteBootcampPersistencePort) {
        return new ReporteBootcampUseCase(reporteBootcampPersistencePort);
    }
}
