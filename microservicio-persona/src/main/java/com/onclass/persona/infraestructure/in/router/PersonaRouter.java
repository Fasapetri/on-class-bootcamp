package com.onclass.persona.infraestructure.in.router;

import com.onclass.persona.infraestructure.in.controller.PersonaController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class PersonaRouter {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(PersonaController personaController) {
        return RouterFunctions.route(
                RequestPredicates.POST("/api/v1/persona")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                personaController::guardarPersona
        );
    }
}
