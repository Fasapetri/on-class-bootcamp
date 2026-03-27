package com.onclass.persona.application.mapper;

import com.onclass.persona.application.dto.InscripcionRequest;
import com.onclass.persona.domain.model.Inscripcion;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
unmappedTargetPolicy = ReportingPolicy.IGNORE,
unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface InscripcionMapper {

    Inscripcion toInscripcion(InscripcionRequest inscripcionRequest);
}
