package com.onclass.persona.domain.usecase;

import com.onclass.persona.domain.api.IPersonaServicePort;
import com.onclass.persona.domain.exception.PersonaErrorMessage;
import com.onclass.persona.domain.exception.PersonaException;
import com.onclass.persona.domain.model.BootcampFecha;
import com.onclass.persona.domain.model.Inscripcion;
import com.onclass.persona.domain.model.Persona;
import com.onclass.persona.domain.spi.IBootcampExternalPort;
import com.onclass.persona.domain.spi.IPersonaPersistencePort;
import com.onclass.persona.domain.spi.IReporteBootcampExternalPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public class PersonaUseCase implements IPersonaServicePort {

    private static final Logger log = LoggerFactory.getLogger(PersonaUseCase.class);
    private final IPersonaPersistencePort personaPersistencePort;
    private final IBootcampExternalPort bootcampExternalPort;
    private final IReporteBootcampExternalPort reporteBootcampExternalPort;

    public PersonaUseCase(IPersonaPersistencePort personaPersistencePort, IBootcampExternalPort bootcampExternalPort, IReporteBootcampExternalPort reporteBootcampExternalPort) {
        this.personaPersistencePort = personaPersistencePort;
        this.bootcampExternalPort = bootcampExternalPort;
        this.reporteBootcampExternalPort = reporteBootcampExternalPort;
    }


    @Override
    public Mono<Persona> guardarPersona(Persona persona) {
        return personaPersistencePort.guardarPersona(persona);
    }

    @Override
    public Mono<Void> inscribirPersonaBootcamp(Inscripcion inscripcion) {
        List<Long> nuevosIds = inscripcion.getIdsBootcamps();

        if (nuevosIds == null || nuevosIds.isEmpty()) {
            return Mono.empty();
        }

        List<Long> idsNuevosUnicos = nuevosIds.stream().distinct().toList();

        return validarExistenciaPersona(inscripcion.getIdPersona())
                .then(personaPersistencePort.buscarIdsBootcampsPorPersona(inscripcion.getIdPersona()).collectList())
                .flatMap(idsActuales -> validarReglasEInscribir(inscripcion.getIdPersona(), idsActuales, idsNuevosUnicos));
    }

    private Mono<Void> validarExistenciaPersona(Long idPersona) {
        return personaPersistencePort.existePersona(idPersona)
                .flatMap(existe -> existe ? Mono.empty() : Mono.error(new PersonaException(PersonaErrorMessage.PERSONA_NO_EXISTE)));
    }

    private Mono<Void> validarReglasEInscribir(Long idPersona, List<Long> idsActuales, List<Long> idsNuevos) {
        if (idsActuales.stream().anyMatch(idsNuevos::contains)) {
            return Mono.error(new PersonaException(PersonaErrorMessage.YA_INSCRITO));
        }

        if (idsActuales.size() + idsNuevos.size() > 5) {
            return Mono.error(new PersonaException(PersonaErrorMessage.MAXIMO_INSCRIPCIONES));
        }

        List<Long> todosIds = Stream.concat(idsActuales.stream(), idsNuevos.stream())
                .distinct()
                .toList();

        return bootcampExternalPort.obtenerFechasBootcamps(todosIds)
                .collectList()
                .flatMap(fechas -> procesarFechasYGuardar(idPersona, idsActuales, idsNuevos, fechas));
    }

    private Mono<Void> procesarFechasYGuardar(Long idPersona, List<Long> idsActuales, List<Long> idsNuevos, List<BootcampFecha> fechas) {
        List<BootcampFecha> bootcampsNuevos = fechas.stream()
                .filter(fecha -> idsNuevos.contains(fecha.getId()))
                .toList();

        List<BootcampFecha> bootcampsActuales = fechas.stream()
                .filter(fecha -> idsActuales.contains(fecha.getId()))
                .toList();

        if (bootcampsNuevos.size() != idsNuevos.size()) {
            return Mono.error(new PersonaException(PersonaErrorMessage.BOOTCAMP_NO_EXISTE));
        }

        if (existeCruceDeFechas(bootcampsNuevos, bootcampsActuales)) {
            return Mono.error(new PersonaException(PersonaErrorMessage.CRUCE_DE_FECHAS));
        }

        return personaPersistencePort.guardarInscripcion(idPersona, idsNuevos)
                .doOnSuccess(inscripcionGuardada -> {
                    personaPersistencePort.buscarPersona(idPersona)
                            .flatMap(personaEncontrada -> {
                                return Flux.fromIterable(idsNuevos)
                                        .flatMap(idBootcamp -> reporteBootcampExternalPort.notificarInscripcionAReporteBootcamp(
                                                idBootcamp,
                                                personaEncontrada.getNombre(),
                                                personaEncontrada.getCorreo()
                                        ))
                                        .then();
                            })
                            .subscribe(
                                    null,
                                    error -> log.error("Fallo al enviar analítica de inscripción: {}", error.getMessage())
                            );
                });
    }

    private boolean existeCruceDeFechas(List<BootcampFecha> nuevos, List<BootcampFecha> actuales) {
        for (BootcampFecha nuevo : nuevos) {
            for (BootcampFecha actual : actuales) {
                if (seCruzan(nuevo, actual)) return true;
            }
        }

        for (int i = 0; i < nuevos.size(); i++) {
            for (int j = i + 1; j < nuevos.size(); j++) {
                if (seCruzan(nuevos.get(i), nuevos.get(j))) return true;
            }
        }

        return false;
    }

    private boolean seCruzan(BootcampFecha fecha1, BootcampFecha fecha2) {
        //se tomara la duracion en DIAS
        LocalDate fechaInicio1 = fecha1.getFechaLanzamiento();
        LocalDate fechaFin1 = fechaInicio1.plusDays(fecha1.getDuracion());

        LocalDate fechaInicio2 = fecha2.getFechaLanzamiento();
        LocalDate fechaFin2 = fechaInicio2.plusDays(fecha2.getDuracion());

        return (fechaInicio1.compareTo(fechaFin2) <= 0) && (fechaFin1.compareTo(fechaInicio2) >= 0);
    }
}
