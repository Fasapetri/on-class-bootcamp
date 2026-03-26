package com.onclass.capacidad.infraestructure.out.adapter;

import com.onclass.capacidad.domain.model.PaginadoCustom;
import com.onclass.capacidad.domain.model.Tecnologia;
import com.onclass.capacidad.domain.spi.ITecnologiaExternalPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

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
                .uri("/validarTecnologias")
                .bodyValue(tecnologias)
                .retrieve()
                .bodyToMono(Boolean.class);
    }

    @Override
    public Mono<Map<Long, List<Tecnologia>>> obtenerTecnologiasPorCapacidad(List<Long> idsCapacidades) {
        return webClient.post()
                .uri("/obtenerTecnologiasPorCapacidad")
                .bodyValue(idsCapacidades)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<Long, List<Tecnologia>>>() {});
    }

    @Override
    public Mono<PaginadoCustom<Long>> obtenerIdsCapacidadesOrdenadosPorCantidad(int pagina, int tamanio, String direccionAscDesc) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/capacidadesOrdenadas")
                        .queryParam("pagina", pagina)
                        .queryParam("tamaño", tamanio)
                        .queryParam("direccion", direccionAscDesc)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<PaginadoCustom<Long>>() {});
    }

    @Override
    public Mono<Void> eliminarRelacionesYTecnologiasHuerfanas(List<Long> idsCapacidades) {
        return webClient.post()
                .uri("/eliminarRelacionesYTecnologiasHuerfanas")
                .bodyValue(idsCapacidades)
                .retrieve()
                .bodyToMono(Void.class);
    }

    private record RelacionCapacidadTecnologiaRequest(Long idCapacidad, List<Long> tecnologias) {}
}
