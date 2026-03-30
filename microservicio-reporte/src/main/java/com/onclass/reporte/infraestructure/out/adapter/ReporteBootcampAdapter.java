package com.onclass.reporte.infraestructure.out.adapter;

import com.onclass.reporte.domain.model.ReporteBootcamp;
import com.onclass.reporte.domain.spi.IReporteBootcampPersistencePort;
import com.onclass.reporte.infraestructure.out.mapper.ReporteBootcampDocumentMapper;
import com.onclass.reporte.infraestructure.out.repository.IReporteBootcampRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ReporteBootcampAdapter implements IReporteBootcampPersistencePort {

    private final IReporteBootcampRepository reporteBootcampRepository;
    private final ReporteBootcampDocumentMapper reporteBootcampDocumentMapper;


    @Override
    public Mono<Void> guardarReporte(ReporteBootcamp reporteBootcamp) {
        return Mono.just(reporteBootcamp)
                .map(reporteBootcampDocumentMapper::toReporteBootcampDocument)
                .flatMap(reporteBootcampRepository::save)
                .then();
    }

    @Override
    public Mono<ReporteBootcamp> buscarReportePorIdBootcamp(Long idBootcamp) {
        return reporteBootcampRepository.findByIdBootcamp(idBootcamp)
                .map(reporteBootcampDocumentMapper::toReporteBootcamp);
    }

    @Override
    public Mono<ReporteBootcamp> buscarBootcampMasInscritos() {
        return reporteBootcampRepository.findFirstByOrderByCantidadPersonasInscritasDesc()
                .map(reporteBootcampDocumentMapper::toReporteBootcamp);
    }

    @Override
    public Flux<ReporteBootcamp> listarReporteBootcamp() {
        return reporteBootcampRepository.findAll()
                .map(reporteBootcampDocumentMapper::toReporteBootcamp);
    }
}
