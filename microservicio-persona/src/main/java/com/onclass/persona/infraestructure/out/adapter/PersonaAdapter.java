package com.onclass.persona.infraestructure.out.adapter;

import com.onclass.persona.domain.model.Persona;
import com.onclass.persona.domain.spi.IPersonaPersistencePort;
import com.onclass.persona.infraestructure.out.mapper.PersonaEntityMapper;
import com.onclass.persona.infraestructure.out.repository.IPersonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PersonaAdapter implements IPersonaPersistencePort {

    private final IPersonaRepository personaRepository;
    private final PersonaEntityMapper personaEntityMapper;

    @Override
    public Mono<Persona> guardarPersona(Persona persona) {
        return Mono.just(persona)
                .map(personaEntityMapper::toPersonaEntity)
                .flatMap(personaRepository::save)
                .map(personaEntityMapper::toPersona);
    }

    @Override
    public Mono<Void> guardarInscripcion(Long idPersona, Long idBootcamp) {
        return null;
    }

    @Override
    public Flux<Long> buscarIdsBootcampsPorPersona(Long idPersona) {
        return null;
    }
}
