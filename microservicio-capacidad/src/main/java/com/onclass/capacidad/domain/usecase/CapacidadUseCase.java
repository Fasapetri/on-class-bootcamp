package com.onclass.capacidad.domain.usecase;

import com.onclass.capacidad.domain.api.ICapacidadServicePort;
import com.onclass.capacidad.domain.exception.CapacidadErrorMessage;
import com.onclass.capacidad.domain.exception.CapacidadException;
import com.onclass.capacidad.domain.model.Capacidad;
import com.onclass.capacidad.domain.model.CapacidadDetalle;
import com.onclass.capacidad.domain.model.PaginadoCustom;
import com.onclass.capacidad.domain.spi.ICapacidadPersistencePort;
import com.onclass.capacidad.domain.spi.ITecnologiaExternalPort;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CapacidadUseCase implements ICapacidadServicePort {

    private final ICapacidadPersistencePort capacidadPersistencePort;
    private final ITecnologiaExternalPort tecnologiaExternalPort;

    public CapacidadUseCase(ICapacidadPersistencePort capacidadPersistencePort, ITecnologiaExternalPort tecnologiaExternalPort) {
        this.capacidadPersistencePort = capacidadPersistencePort;
        this.tecnologiaExternalPort = tecnologiaExternalPort;
    }

    @Override
    public Mono<Capacidad> guardarCapacidad(Capacidad capacidad) {

        return validarTecnologia(capacidad.getTecnologias())
                .flatMap(valido -> tecnologiaExternalPort.existenTecnologias(capacidad.getTecnologias()))
                .flatMap(existenTecnologias -> {
                    if(!existenTecnologias){
                        return Mono.error(new CapacidadException(CapacidadErrorMessage.TECNOLOGIA_NO_EXISTE));
                    }
                    return capacidadPersistencePort.guardarCapacidad(capacidad);
                })
                .flatMap(capacidadGuardada ->
                    tecnologiaExternalPort.guardarRelacionCapacidadTecnologia(capacidadGuardada.getId(), capacidadGuardada.getTecnologias())
                            .thenReturn(capacidadGuardada)
                );
    }

    @Override
    public Mono<PaginadoCustom<CapacidadDetalle>> listarCapacidades(int pagina, int tamanio, String ordenarPor, String direccion) {

        if(ordenarPor.equalsIgnoreCase("nombre")){
            return capacidadPersistencePort.listarCapacidadesPaginadas(pagina, tamanio, direccion)
                    .flatMap(paginadoCapacidades -> llenarConTecnologias(paginadoCapacidades));

        } else
            if(ordenarPor.equalsIgnoreCase("cantidadTecnologias")){
                return tecnologiaExternalPort.obtenerIdsCapacidadesOrdenadosPorCantidad(pagina, tamanio, direccion)
                        .flatMap(idsCapacidades -> paginadoDesdeIdsCapacidades(idsCapacidades));
        } else {
                return capacidadPersistencePort.listarCapacidadesPaginadas(pagina, tamanio, "asc")
                        .flatMap(paginadoCapacidades -> llenarConTecnologias(paginadoCapacidades));
            }


    }

    private Mono<Boolean> validarTecnologia(List<Long> tecnologias){
        if(tecnologias == null || tecnologias.size() < 3){
            return Mono.error(new CapacidadException(CapacidadErrorMessage.TECNOLOGIAS_MINIMAS));
        }
        if(tecnologias.size() > 20){
            return Mono.error(new CapacidadException(CapacidadErrorMessage.TECNOLOGIAS_MAXIMAS));
        }

        Set<Long> tecnologiasNoDuplicadas = new HashSet<>(tecnologias);
        if(tecnologiasNoDuplicadas.size() != tecnologias.size()){
            return Mono.error(new CapacidadException(CapacidadErrorMessage.TECNOLOGIAS_DUPLICADAS));
        }
        return Mono.just(true);
    }

    private Mono<PaginadoCustom<CapacidadDetalle>> llenarConTecnologias(PaginadoCustom<Capacidad> paginadoCapacidades){
        List<Long> idsCapacidades = paginadoCapacidades.getContenido()
                .stream()
                .map(Capacidad::getId)
                .toList();

        if(idsCapacidades.isEmpty()){
            return Mono.just(new PaginadoCustom<>(List.of(), paginadoCapacidades.getTotalPaginas(), paginadoCapacidades.getTotalElementos()));
        }

        return tecnologiaExternalPort.obtenerTecnologiasPorCapacidad(idsCapacidades)
                .map(mapaTecnologias ->{
                    List<CapacidadDetalle> detalle = paginadoCapacidades.getContenido()
                            .stream()
                            .map(capacidad -> new CapacidadDetalle(
                                    capacidad.getId(),
                                    capacidad.getNombre(),
                                    capacidad.getDescripcion(),
                                    mapaTecnologias.getOrDefault(capacidad.getId(), List.of())
                                    )

                            ).toList();
                    return new PaginadoCustom<>(detalle, paginadoCapacidades.getTotalPaginas(), paginadoCapacidades.getTotalElementos());
                });
    }

    private Mono<PaginadoCustom<CapacidadDetalle>> paginadoDesdeIdsCapacidades(PaginadoCustom<Long> paginadoIds) {
        if(paginadoIds.getContenido().isEmpty()){
            return Mono.just(new PaginadoCustom<>(List.of(), paginadoIds.getTotalPaginas(), paginadoIds.getTotalElementos()));
        }

        return capacidadPersistencePort.obtenerCapacidadesPorIds(paginadoIds.getContenido())
                .collectList()
                .flatMap(capacidades -> {
                    List<Capacidad> capacidadesOrdenadas = paginadoIds.getContenido().stream()
                            .flatMap(id -> capacidades.stream().filter(c -> c.getId().equals(id)))
                            .toList();

                    PaginadoCustom<Capacidad> paginaTransformada = new PaginadoCustom<>(
                            capacidadesOrdenadas,
                            paginadoIds.getTotalPaginas(),
                            paginadoIds.getTotalElementos()
                    );
                    return llenarConTecnologias(paginaTransformada);
                });
    }
}
