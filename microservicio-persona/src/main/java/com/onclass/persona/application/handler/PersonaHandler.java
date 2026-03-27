package com.onclass.persona.application.handler;

import com.onclass.persona.application.dto.InscripcionRequest;
import com.onclass.persona.application.dto.PersonaRequest;
import com.onclass.persona.application.dto.PersonaResponse;
import com.onclass.persona.application.mapper.InscripcionMapper;
import com.onclass.persona.application.mapper.PersonaMapper;
import com.onclass.persona.domain.api.IPersonaServicePort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class PersonaHandler implements IPersonaHandler{

    private final IPersonaServicePort personaServicePort;
    private final PersonaMapper personaMapper;
    private final InscripcionMapper inscripcionMapper;

    public PersonaHandler(IPersonaServicePort personaServicePort, PersonaMapper personaMapper, InscripcionMapper inscripcionMapper) {
        this.personaServicePort = personaServicePort;
        this.personaMapper = personaMapper;
        this.inscripcionMapper = inscripcionMapper;
    }

    @Override
    public Mono<PersonaResponse> guardarPersona(PersonaRequest personaRequest) {
        return personaServicePort.guardarPersona(personaMapper.toPersona(personaRequest))
                .map(personaMapper::toPersonaResponse);
    }

    @Override
    public Mono<Void> inscribirPersonaBootcamp(InscripcionRequest inscripcionRequest) {
        return personaServicePort.inscribirPersonaBootcamp(inscripcionMapper.toInscripcion(inscripcionRequest));
    }
}
