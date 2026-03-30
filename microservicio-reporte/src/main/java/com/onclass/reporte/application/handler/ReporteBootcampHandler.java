package com.onclass.reporte.application.handler;

import com.onclass.reporte.application.dto.ReporteBootcampRequest;
import com.onclass.reporte.application.dto.ReporteBootcampResponse;
import com.onclass.reporte.application.mapper.ReporteBootcampMapper;
import com.onclass.reporte.domain.api.IReporteBootcampServicePort;
import com.onclass.reporte.domain.model.ReporteBootcamp;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReporteBootcampHandler implements IReporteBootcampHandler {

    private final IReporteBootcampServicePort reporteBootcampServicePort;
    private final ReporteBootcampMapper reporteBootcampMapper;

    public ReporteBootcampHandler(IReporteBootcampServicePort reporteBootcampServicePort, ReporteBootcampMapper reporteBootcampMapper) {
        this.reporteBootcampServicePort = reporteBootcampServicePort;
        this.reporteBootcampMapper = reporteBootcampMapper;
    }

    @Override
    public Mono<Void> guardarReporte(ReporteBootcampRequest reporteBootcampRequest) {
        return Mono.just(reporteBootcampRequest)
                .map(reporteBootcampMapper::toReporteBootcamp)
                .flatMap(reporteBootcampServicePort::guardarReporte)
                .then();
    }

    @Override
    public Mono<Void> registrarNuevaInscripcion(Long idBootcamp, String nombrePersona, String correoPersona) {
        return reporteBootcampServicePort.registrarNuevaInscripcion(idBootcamp, nombrePersona, correoPersona);
    }

    @Override
    public Mono<ReporteBootcamp> obtenerBootcampMasInscritos() {
        return reporteBootcampServicePort.obtenerBootcampMasInscritos();
    }

    @Override
    public Flux<ReporteBootcampResponse> listarReporteBootcamp() {
        return reporteBootcampServicePort.listarReporteBootcamp()
                .map(reporteBootcampMapper::toReporteBootcamp);
    }
}
