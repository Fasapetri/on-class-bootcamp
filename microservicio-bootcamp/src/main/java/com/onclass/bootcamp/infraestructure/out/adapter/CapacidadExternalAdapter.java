package com.onclass.bootcamp.infraestructure.out.adapter;

import com.onclass.bootcamp.domain.spi.ICapacidadExternalPort;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class CapacidadExternalAdapter implements ICapacidadExternalPort {

    private final WebClient webClient;

    public CapacidadExternalAdapter(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081/api/v1/capacidad").build();
    }

    @Override
    public Mono<Boolean> guardarRelacionBootcampCapacidad(Long idBootcamp, List<Long> capacidades) {
        RelacionBootcampCapacidadRequest relacion = new RelacionBootcampCapacidadRequest(idBootcamp, capacidades);

        return webClient.post()
                .uri("/guardarRelacionBootcampCapacidad")
                .bodyValue(relacion)
                .retrieve()
                .toBodilessEntity()
                .thenReturn(true);
    }

    @Override
    public Mono<Boolean> existenCapacidades(List<Long> capacidades) {
        return webClient.post()
                .uri("/validarCapacidades")
                .bodyValue(capacidades)
                .retrieve()
                .bodyToMono(Boolean.class);
    }

    private record RelacionBootcampCapacidadRequest(Long idBootcamp, List<Long> capacidades) {}
}
