package com.onclass.persona.infraestructure.out.mapper;

import com.onclass.persona.domain.model.Persona;
import com.onclass.persona.infraestructure.out.entity.PersonaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
unmappedTargetPolicy = ReportingPolicy.IGNORE,
unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PersonaEntityMapper {

    PersonaEntity toPersonaEntity(Persona persona);
    Persona toPersona(PersonaEntity personaEntity);
}
