package com.onclass.persona.infraestructure.out.adapter;

import com.onclass.persona.domain.model.Persona;
import com.onclass.persona.domain.spi.IPersonaPersistencePort;
import com.onclass.persona.infraestructure.out.entity.BootcampPersonaEntity;
import com.onclass.persona.infraestructure.out.mapper.PersonaEntityMapper;
import com.onclass.persona.infraestructure.out.repository.IBootcampPersonaRepository;
import com.onclass.persona.infraestructure.out.repository.IPersonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PersonaAdapter implements IPersonaPersistencePort {

    private final IPersonaRepository personaRepository;
    private final PersonaEntityMapper personaEntityMapper;
    private final IBootcampPersonaRepository bootcampPersonaRepository;

    @Override
    public Mono<Persona> guardarPersona(Persona persona) {
        return Mono.just(persona)
                .map(personaEntityMapper::toPersonaEntity)
                .flatMap(personaRepository::save)
                .map(personaEntityMapper::toPersona);
    }

    @Override
    public Mono<Void> guardarInscripcion(Long idPersona, List<Long> idsBootcamps) {
        return Flux.fromIterable(idsBootcamps)
                .map(idBootcamp -> new BootcampPersonaEntity(idPersona, idBootcamp))
                .flatMap(bootcampPersonaRepository::save)
                .then();
    }

    @Override
    public Flux<Long> buscarIdsBootcampsPorPersona(Long idPersona) {
        return bootcampPersonaRepository.findByIdPersona(idPersona)
                .map(BootcampPersonaEntity::getIdBootcamp);
    }

    @Override
    public Mono<Boolean> existePersona(Long idPersona) {
        return personaRepository.existsById(idPersona);
    }

    @Override
    public Mono<Persona> buscarPersona(Long idPersona) {
        return personaRepository.findById(idPersona)
                .map(personaEntityMapper::toPersona);
    }
}
