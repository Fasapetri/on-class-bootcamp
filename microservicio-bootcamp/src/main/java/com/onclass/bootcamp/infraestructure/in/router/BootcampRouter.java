package com.onclass.bootcamp.infraestructure.in.router;

import com.onclass.bootcamp.infraestructure.in.controller.BootcampController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class BootcampRouter {

    @Bean
    public RouterFunction<ServerResponse> bootcampRouterFunction(BootcampController bootcampController) {
        return RouterFunctions.route(
                RequestPredicates.POST("/api/v1/bootcamp")
                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                bootcampController::guardarBootcamp
                )
                .andRoute(
                        RequestPredicates.GET("/api/v1/bootcamp")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        bootcampController::listarBootcamps
                );
    }
}
