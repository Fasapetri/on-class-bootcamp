package com.onclass.reporte.domain.usecase;

import com.onclass.reporte.domain.api.IReporteBootcampServicePort;
import com.onclass.reporte.domain.model.PersonaInscritaReporte;
import com.onclass.reporte.domain.model.ReporteBootcamp;
import com.onclass.reporte.domain.spi.IReporteBootcampPersistencePort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

public class ReporteBootcampUseCase implements IReporteBootcampServicePort {

    private final IReporteBootcampPersistencePort reporteBootcampPersistencePort;

    public ReporteBootcampUseCase(IReporteBootcampPersistencePort reporteBootcampPersistencePort) {
        this.reporteBootcampPersistencePort = reporteBootcampPersistencePort;
    }

    @Override
    public Mono<Void> guardarReporte(ReporteBootcamp reporteBootcamp) {
        return reporteBootcampPersistencePort.guardarReporte(reporteBootcamp);
    }

    @Override
    public Mono<Void> registrarNuevaInscripcion(Long idBootcamp, String nombrePersona, String correoPersona) {

        return reporteBootcampPersistencePort.buscarReportePorIdBootcamp(idBootcamp)
                .flatMap(reporte -> {

                    if (reporte.getPersonasInscritas() == null) {
                        reporte.setPersonasInscritas(new ArrayList<>());
                    }

                    reporte.getPersonasInscritas().add(new PersonaInscritaReporte(nombrePersona, correoPersona));
                    int cantidadActual = reporte.getCantidadPersonasInscritas() == null ? 0 : reporte.getCantidadPersonasInscritas();
                    reporte.setCantidadPersonasInscritas(cantidadActual + 1);

                    return reporteBootcampPersistencePort.guardarReporte(reporte);
                });
    }

    @Override
    public Mono<ReporteBootcamp> obtenerBootcampMasInscritos() {
        return reporteBootcampPersistencePort.buscarBootcampMasInscritos();
    }

    @Override
    public Flux<ReporteBootcamp> listarReporteBootcamp() {
        return reporteBootcampPersistencePort.listarReporteBootcamp();
    }
}
