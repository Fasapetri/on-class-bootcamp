package com.onclass.persona.infraestructure.out.adapter;

import com.onclass.persona.domain.spi.IReporteBootcampExternalPort;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class ReporteBootcampExternalAdapter implements IReporteBootcampExternalPort {

    private WebClient webClient;

    public ReporteBootcampExternalAdapter(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8084/api/v1/reporte").build();
    }

    @Override
    public Mono<Void> notificarInscripcionAReporteBootcamp(Long idBootcamp, String nombrePersona, String correoPersona) {

        Map<String, String> body = Map.of("nombre", nombrePersona, "correo", correoPersona);

        return webClient.post()
                .uri("/bootcamp/{idBootcamp}/inscripcion", idBootcamp)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
