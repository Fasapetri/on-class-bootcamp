package com.onclass.persona.domain.usecase;

import com.onclass.persona.domain.api.IPersonaServicePort;
import com.onclass.persona.domain.exception.PersonaErrorMessage;
import com.onclass.persona.domain.exception.PersonaException;
import com.onclass.persona.domain.model.BootcampFecha;
import com.onclass.persona.domain.model.Persona;
import com.onclass.persona.domain.spi.IBootcampExternalPort;
import com.onclass.persona.domain.spi.IPersonaPersistencePort;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class PersonaUseCase implements IPersonaServicePort {

    private final IPersonaPersistencePort personaPersistencePort;
    private final IBootcampExternalPort bootcampExternalPort;

    public PersonaUseCase(IPersonaPersistencePort personaPersistencePort, IBootcampExternalPort bootcampExternalPort) {
        this.personaPersistencePort = personaPersistencePort;
        this.bootcampExternalPort = bootcampExternalPort;
    }


    @Override
    public Mono<Persona> guardarPersona(Persona persona) {
        return personaPersistencePort.guardarPersona(persona);
    }

    @Override
    public Mono<Void> inscribirPersonaBootcamp(Long personaId, Long bootcampId) {
        return null;
    }
}
