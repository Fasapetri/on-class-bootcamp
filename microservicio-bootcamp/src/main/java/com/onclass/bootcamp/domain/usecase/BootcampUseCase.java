package com.onclass.bootcamp.domain.usecase;

import com.onclass.bootcamp.domain.api.IBootcampServicePort;
import com.onclass.bootcamp.domain.exception.BootcampErrorMessage;
import com.onclass.bootcamp.domain.exception.BootcampException;
import com.onclass.bootcamp.domain.model.Bootcamp;
import com.onclass.bootcamp.domain.model.BootcampDetalle;
import com.onclass.bootcamp.domain.model.PaginaCustom;
import com.onclass.bootcamp.domain.spi.IBootcampPersistencePort;
import com.onclass.bootcamp.domain.spi.ICapacidadExternalPort;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BootcampUseCase implements IBootcampServicePort {

    private final IBootcampPersistencePort bootcampPersistencePort;
    private final ICapacidadExternalPort capacidadExternalPort;

    public BootcampUseCase(IBootcampPersistencePort bootcampPersistencePort, ICapacidadExternalPort capacidadExternalPort) {
        this.bootcampPersistencePort = bootcampPersistencePort;
        this.capacidadExternalPort = capacidadExternalPort;
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
        );

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
}
