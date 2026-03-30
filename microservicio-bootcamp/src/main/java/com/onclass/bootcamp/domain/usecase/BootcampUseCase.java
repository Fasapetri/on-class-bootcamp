package com.onclass.bootcamp.domain.usecase;

import com.onclass.bootcamp.domain.api.IBootcampServicePort;
import com.onclass.bootcamp.domain.exception.BootcampErrorMessage;
import com.onclass.bootcamp.domain.exception.BootcampException;
import com.onclass.bootcamp.domain.model.*;
import com.onclass.bootcamp.domain.spi.IBootcampPersistencePort;
import com.onclass.bootcamp.domain.spi.ICapacidadExternalPort;
import com.onclass.bootcamp.domain.spi.IReporteBootcampExternalPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BootcampUseCase implements IBootcampServicePort {

    private static final Logger log = LoggerFactory.getLogger(BootcampUseCase.class);
    private final IBootcampPersistencePort bootcampPersistencePort;
    private final ICapacidadExternalPort capacidadExternalPort;
    private final IReporteBootcampExternalPort reporteBootcampExternalPort;

    public BootcampUseCase(IBootcampPersistencePort bootcampPersistencePort, ICapacidadExternalPort capacidadExternalPort, IReporteBootcampExternalPort reporteBootcampExternalPort) {
        this.bootcampPersistencePort = bootcampPersistencePort;
        this.capacidadExternalPort = capacidadExternalPort;
        this.reporteBootcampExternalPort = reporteBootcampExternalPort;
    }

    @Override
    public Mono<Bootcamp> guardarBootcamp(Bootcamp bootcamp) {
        return bootcampPersistencePort.existeBootcampPorNombre(bootcamp.getNombre())
                .flatMap( esValido -> {
                    if(esValido){
                        return Mono.error(new BootcampException(BootcampErrorMessage.BOOTCAMP_DUPLICADO));
                    }
                    return validarCapacidades(bootcamp.getCapacidades())
                            .flatMap(valido -> capacidadExternalPort.existenCapacidades(bootcamp.getCapacidades()))
                            .flatMap(existenTodas -> {
                                if(!existenTodas){
                                    return Mono.error(new BootcampException(BootcampErrorMessage.CAPACIDAD_NO_EXISTE));
                                }
                                return bootcampPersistencePort.guardarBootcamp(bootcamp);
                            });
                }).flatMap(bootcampGuardado ->
                        capacidadExternalPort.guardarRelacionBootcampCapacidad(bootcampGuardado.getId(), bootcampGuardado.getCapacidades())
                                                .thenReturn(bootcampGuardado)
        ).doOnSuccess(bootcampGuardado -> generarYEnviarReporteBootcamp(bootcampGuardado, bootcampGuardado.getCapacidades())
                .subscribe());

    }

    @Override
    public Mono<PaginaCustom<BootcampDetalle>> buscarBootcamps(int numeroPagina, int tamanioPagina, String orden, String filtro) {

        if(orden.equalsIgnoreCase("nombre")){
            return bootcampPersistencePort.listarBootcampsPaginados(numeroPagina, tamanioPagina, filtro)
                    .flatMap(this::enriquecerConCapacidades);
        } else if (orden.equalsIgnoreCase("cantidadCapacidades")){
            return capacidadExternalPort.obtenerIdsBootcampsOrdenadosPorCantidad(numeroPagina, tamanioPagina, filtro)
                    .flatMap(this::construirPaginaDesdeIds);
        } else {
            return bootcampPersistencePort.listarBootcampsPaginados(numeroPagina, tamanioPagina, "asc")
                    .flatMap(this::enriquecerConCapacidades);
        }
    }

    @Override
    public Mono<Void> eliminarBootcamp(Long idBootcamp) {
        return capacidadExternalPort.eliminarRelacionesYCapacidadesHuerfanas(idBootcamp)
                .then(bootcampPersistencePort.eliminarBootcamp(idBootcamp));
    }

    @Override
    public Flux<Bootcamp> buscarBootcampsPorIds(List<Long> idsBootcamps) {
        if (idsBootcamps == null || idsBootcamps.isEmpty()) return Flux.empty();
        return bootcampPersistencePort.obtenerBootcampsPorIds(idsBootcamps);
    }

    private Mono<Boolean> validarCapacidades(List<Long> capacidades){

        if(capacidades == null || capacidades.isEmpty()){
            return Mono.error(new BootcampException(BootcampErrorMessage.CAPACIDADES_MINIMAS));
        }
        if(capacidades.size() > 4){
            return Mono.error(new BootcampException(BootcampErrorMessage.CAPACIDADES_MAXIMAS));
        }

        Set<Long> capacidadesNoRepetidas = new HashSet<>(capacidades);
        if(capacidadesNoRepetidas.size() != capacidades.size()){
            return Mono.error(new BootcampException(BootcampErrorMessage.CAPACIDADES_DUPLICADAS));
        }
        return Mono.just(true);
    }

    private Mono<PaginaCustom<BootcampDetalle>> enriquecerConCapacidades(PaginaCustom<Bootcamp> bootcamps){
        List<Long> idsBootcamps = bootcamps.getContenido().stream().map(Bootcamp::getId).toList();

        if(idsBootcamps.isEmpty()){
            return Mono.just(new PaginaCustom<>(List.of(), bootcamps.getTotalPaginas(), bootcamps.getTotalElementos()));
        }

        return capacidadExternalPort.obtenerCapacidadesPorBootcamp(idsBootcamps)
                .map(mapaCapacidades -> {
                    List<BootcampDetalle> detalles = bootcamps.getContenido().stream()
                            .map(bootcamp -> new BootcampDetalle(
                                    bootcamp.getId(),
                                    bootcamp.getNombre(),
                                    bootcamp.getDescripcion(),
                                    bootcamp.getFechaLanzamiento(),
                                    bootcamp.getDuracion(),
                                    mapaCapacidades.getOrDefault(bootcamp.getId(), List.of())
                            )).toList();
                    return new PaginaCustom<>(detalles, bootcamps.getTotalPaginas(), bootcamps.getTotalElementos());
                });

    }

    private Mono<PaginaCustom<BootcampDetalle>> construirPaginaDesdeIds(PaginaCustom<Long> idsBootcamps) {

        if(idsBootcamps.getContenido().isEmpty()){
            return Mono.just(new PaginaCustom<>(List.of(), idsBootcamps.getTotalPaginas(), idsBootcamps.getTotalElementos()));
        }

        return bootcampPersistencePort.obtenerBootcampsPorIds(idsBootcamps.getContenido())
                .collectList()
                .flatMap(bootcamps -> {
                    List<Bootcamp> bootcampsOrdenados = idsBootcamps.getContenido().stream()
                            .flatMap(id -> bootcamps.stream().filter(b -> b.getId().equals(id)))
                            .toList();

                    PaginaCustom<Bootcamp> paginaReconstruida = new PaginaCustom<>(bootcampsOrdenados, idsBootcamps.getTotalPaginas(), idsBootcamps.getTotalElementos());
                    return enriquecerConCapacidades(paginaReconstruida);
                });
    }

    private Mono<Void> generarYEnviarReporteBootcamp(Bootcamp bootcamp, List<Long> idsCapacidades){

        return capacidadExternalPort.obtenerCapacidadesPorBootcamp(List.of(bootcamp.getId()))
                .flatMap(mapaCapacidades -> {
                    List<Capacidad> capacidades = mapaCapacidades.getOrDefault(bootcamp.getId(), List.of());

                    int totalTecnologias = capacidades.stream().mapToInt(c -> c.getTecnologias().size()).sum();

                    List<String> nombresCapacidades = capacidades.stream()
                            .map(Capacidad::getNombre)
                            .toList();

                    List<String> nombresTecnologias = capacidades.stream()
                            .flatMap(c -> c.getTecnologias().stream())
                            .map(Tecnologia::getNombre)
                            .distinct()
                            .toList();

                    ReporteBootcamp reporte = new ReporteBootcamp(
                            null,
                            bootcamp.getId(),
                            bootcamp.getNombre(),
                            bootcamp.getDescripcion(),
                            bootcamp.getFechaLanzamiento(),
                            bootcamp.getDuracion(),
                            idsCapacidades.size(),
                            totalTecnologias,
                            0,
                            nombresCapacidades,
                            nombresTecnologias,
                            new ArrayList<>()
                    );
                    return reporteBootcampExternalPort.enviarReporteNuevoBootcamp(reporte);
                })
                .onErrorResume(error -> {
                    log.error("Error en el envio del reporte del bootcamp ID {}: {}", bootcamp.getId(), error.getMessage(), error);
                    return Mono.empty();
                });
    }
}
