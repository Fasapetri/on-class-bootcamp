package com.onclass.tecnologia.infraestructure.in.router;

import com.onclass.tecnologia.infraestructure.in.controller.TecnologiaController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class TecnologiaRouter {

    @Bean
    public RouterFunction<ServerResponse> tecnologiaRoutes(TecnologiaController tecnologiaController) {
        return RouterFunctions.route(
                RequestPredicates.POST("/api/v1/tecnologias")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                tecnologiaController::guardarTecnologia
        );
    }
}
