package com.onclass.persona.application.handler;

import com.onclass.persona.application.dto.PersonaRequest;
import com.onclass.persona.application.dto.PersonaResponse;
import com.onclass.persona.application.mapper.PersonaMapper;
import com.onclass.persona.domain.api.IPersonaServicePort;
import com.onclass.persona.domain.model.Persona;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PersonaHandler implements IPersonaHandler{

    private final IPersonaServicePort personaServicePort;
    private final PersonaMapper personaMapper;

    public PersonaHandler(IPersonaServicePort personaServicePort, PersonaMapper personaMapper) {
        this.personaServicePort = personaServicePort;
        this.personaMapper = personaMapper;
    }

    @Override
    public Mono<PersonaResponse> guardarPersona(PersonaRequest personaRequest) {
        return personaServicePort.guardarPersona(personaMapper.toPersona(personaRequest))
                .map(personaMapper::toPersonaResponse);
    }
}
