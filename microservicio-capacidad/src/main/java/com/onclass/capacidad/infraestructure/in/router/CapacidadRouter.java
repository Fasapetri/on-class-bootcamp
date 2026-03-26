package com.onclass.capacidad.infraestructure.in.router;

import com.onclass.capacidad.infraestructure.in.controller.CapacidadController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CapacidadRouter {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(CapacidadController capacidadController) {
        return RouterFunctions.route(
                RequestPredicates.POST("/api/v1/capacidad")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                capacidadController::guardarCapacidad
        )
                .andRoute(
                        RequestPredicates.GET("/api/v1/capacidad")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        capacidadController::listarCapacidadesPaginadas
                )
                .andRoute(
                        RequestPredicates.POST("/api/v1/capacidad/guardarRelacionBootcampCapacidad")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        capacidadController::guardarRelacionBootcampCapacidad
                )
                .andRoute(
                        RequestPredicates.POST("/api/v1/capacidad/validarCapacidades")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        capacidadController::validarCapacidades
                )
                .andRoute(
                        RequestPredicates.GET("/api/v1/capacidad/bootcampsOrdenados")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        capacidadController::obtenerIdsBootcampsOrdenados
                )
                .andRoute(
                        RequestPredicates.POST("/api/v1/capacidad/obtenerCapacidadesPorBootcamp")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        capacidadController::obtenerCapacidadesPorBootcamps
                )
                .andRoute(
                        RequestPredicates.DELETE("/api/v1/capacidad/eliminarRelacionesYCapacidadesHuerfanas/{id}")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        capacidadController::eliminarRelacionesYCapacidadesHuerfanas
                );
    }
}
