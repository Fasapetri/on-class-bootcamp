package com.onclass.bootcamp.infraestructure.out.adapter;

import com.onclass.bootcamp.domain.model.Capacidad;
import com.onclass.bootcamp.domain.model.PaginaCustom;
import com.onclass.bootcamp.domain.spi.ICapacidadExternalPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

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

    @Override
    public Mono<PaginaCustom<Long>> obtenerIdsBootcampsOrdenadosPorCantidad(int numeroPagina, int tamanioPagina, String orden) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/bootcampsOrdenados")
                        .queryParam("pagina", numeroPagina)
                        .queryParam("tamanio", tamanioPagina)
                        .queryParam("orden", orden)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<PaginaCustom<Long>>() {});
    }

    @Override
    public Mono<Map<Long, List<Capacidad>>> obtenerCapacidadesPorBootcamp(List<Long> bootcampsIds) {
        return webClient.post()
                .uri("/obtenerCapacidadesPorBootcamp")
                .bodyValue(bootcampsIds)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<Long, List<Capacidad>>>() {});
    }

    @Override
    public Mono<Void> eliminarRelacionesYCapacidadesHuerfanas(Long idBootcamp) {
        return webClient.delete()
                .uri("/eliminarRelacionesYCapacidadesHuerfanas/{id}", idBootcamp)
                .retrieve()
                .bodyToMono(Void.class);
    }

    private record RelacionBootcampCapacidadRequest(Long idBootcamp, List<Long> capacidades) {}
}
