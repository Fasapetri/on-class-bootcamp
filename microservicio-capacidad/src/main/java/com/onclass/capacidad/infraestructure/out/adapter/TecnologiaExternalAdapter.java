package com.onclass.capacidad.infraestructure.out.adapter;

import com.onclass.capacidad.domain.spi.ITecnologiaExternalPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class TecnologiaExternalAdapter implements ITecnologiaExternalPort {

    private final WebClient webClient;

    public TecnologiaExternalAdapter(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/api/v1/tecnologias").build();
    }

    @Override
    public Mono<Boolean> guardarRelacionCapacidadTecnologia(Long idCapacidad, List<Long> tecnologias) {

        RelacionCapacidadTecnologiaRequest relacion = new RelacionCapacidadTecnologiaRequest(idCapacidad, tecnologias);

        return webClient.post()
                .uri("/relacionCapacidadTecnologia")
                .bodyValue(relacion)
                .retrieve()
                .toBodilessEntity()
                .thenReturn(true);
    }

    @Override
    public Mono<Boolean> existenTecnologias(List<Long> tecnologias) {
        return webClient.post()
                .uri("/validarTecnologia")
                .bodyValue(tecnologias)
                .retrieve()
                .bodyToMono(Boolean.class);
    }

    private record RelacionCapacidadTecnologiaRequest(Long idCapacidad, List<Long> tecnologias) {}
}
