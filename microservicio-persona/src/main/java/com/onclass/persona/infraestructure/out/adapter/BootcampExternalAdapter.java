package com.onclass.persona.infraestructure.out.adapter;

import com.onclass.persona.domain.model.BootcampFecha;
import com.onclass.persona.domain.spi.IBootcampExternalPort;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

@Component
public class BootcampExternalAdapter implements IBootcampExternalPort {

    private final WebClient webClient;

    public BootcampExternalAdapter(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8082/api/v1/bootcamp").build();
    }

    @Override
    public Flux<BootcampFecha> obtenerFechasBootcamps(List<Long> idsBootcamps) {
        if (idsBootcamps.isEmpty() || idsBootcamps == null) return Flux.empty();

        return webClient.post()
                .uri("/obtenerFechasBootcamps")
                .bodyValue(idsBootcamps)
                .retrieve()
                .bodyToFlux(BootcampFecha.class);
    }
}
