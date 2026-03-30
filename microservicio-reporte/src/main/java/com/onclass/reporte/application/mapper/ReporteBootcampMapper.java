package com.onclass.reporte.application.mapper;

import com.onclass.reporte.application.dto.ReporteBootcampRequest;
import com.onclass.reporte.application.dto.ReporteBootcampResponse;
import com.onclass.reporte.domain.model.ReporteBootcamp;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReporteBootcampMapper {

    ReporteBootcamp toReporteBootcamp(ReporteBootcampRequest reporteBootcampRequest);
    ReporteBootcampResponse toReporteBootcamp(ReporteBootcamp reporteBootcamp);
}
