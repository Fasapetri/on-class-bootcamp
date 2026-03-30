package com.onclass.reporte.infraestructure.in.router;

import com.onclass.reporte.infraestructure.in.controller.ReporteBootcampController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ReporteBootcampRouter {

    @Bean
    public RouterFunction<ServerResponse> reporteBootcampRouterFunction(ReporteBootcampController reporteBootcampController) {
        return RouterFunctions.route(
                RequestPredicates.POST("/api/v1/reporte/bootcamp")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                reporteBootcampController::guardarReporte
        )
                .andRoute(
                        RequestPredicates.POST("/api/v1/reporte/bootcamp/{idBootcamp}/inscripcion")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        reporteBootcampController::registrarNuevaInscripcion
                )
                .andRoute(
                        RequestPredicates.GET("/api/v1/reporte/bootcampMayorCantidadPersonas")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        reporteBootcampController::obtenerBootcampMasInscritos
                )
                .andRoute(
                        RequestPredicates.GET("/api/v1/reporte/bootcampsRegistrados")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        reporteBootcampController::listarReporteBootcamps
                );
    }
}
