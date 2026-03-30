package com.onclass.reporte.infraestructure.out.mapper;

import com.onclass.reporte.domain.model.ReporteBootcamp;
import com.onclass.reporte.infraestructure.out.document.ReporteBootcampDocument;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
unmappedSourcePolicy = ReportingPolicy.IGNORE,
unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReporteBootcampDocumentMapper {

    ReporteBootcampDocument toReporteBootcampDocument(ReporteBootcamp reporteBootcamp);
    ReporteBootcamp toReporteBootcamp(ReporteBootcampDocument reporteBootcampDocument);
}
