package com.onclass.persona.application.mapper;

import com.onclass.persona.application.dto.PersonaRequest;
import com.onclass.persona.application.dto.PersonaResponse;
import com.onclass.persona.domain.model.Persona;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
unmappedTargetPolicy = ReportingPolicy.IGNORE,
unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PersonaMapper {

    PersonaResponse toPersonaResponse(Persona persona);
    Persona toPersona(PersonaRequest personaRequest);
}
