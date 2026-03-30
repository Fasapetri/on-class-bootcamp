package com.onclass.reporte.infraestructure.out.repository;

import com.onclass.reporte.infraestructure.out.document.ReporteBootcampDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface IReporteBootcampRepository extends ReactiveMongoRepository<ReporteBootcampDocument, String> {

    Mono<ReporteBootcampDocument> findByIdBootcamp(Long idBootcamp);

    Mono<ReporteBootcampDocument> findFirstByOrderByCantidadPersonasInscritasDesc();
}
