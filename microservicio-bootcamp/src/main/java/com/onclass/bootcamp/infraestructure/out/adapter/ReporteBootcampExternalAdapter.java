package com.onclass.bootcamp.infraestructure.out.adapter;

import com.onclass.bootcamp.domain.model.ReporteBootcamp;
import com.onclass.bootcamp.domain.spi.IReporteBootcampExternalPort;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ReporteBootcampExternalAdapter implements IReporteBootcampExternalPort {

    private final WebClient webClient;

    public ReporteBootcampExternalAdapter(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8084/api/v1/reporte").build();
    }

    @Override
    public Mono<Void> enviarReporteNuevoBootcamp(ReporteBootcamp reporteBootcamp) {
        return webClient.post()
                .uri("/bootcamp")
                .bodyValue(reporteBootcamp)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
