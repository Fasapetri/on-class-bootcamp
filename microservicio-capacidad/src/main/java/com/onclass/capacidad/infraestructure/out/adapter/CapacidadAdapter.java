package com.onclass.capacidad.infraestructure.out.adapter;

import com.onclass.capacidad.domain.model.BootcampCapacidadProjection;
import com.onclass.capacidad.domain.model.Capacidad;
import com.onclass.capacidad.domain.model.PaginadoCustom;
import com.onclass.capacidad.domain.spi.ICapacidadPersistencePort;
import com.onclass.capacidad.infraestructure.out.entity.BootcampCapacidadEntity;
import com.onclass.capacidad.infraestructure.out.mapper.BootcampCapacidadProjectionEntityMapper;
import com.onclass.capacidad.infraestructure.out.mapper.CapacidadEntityMapper;
import com.onclass.capacidad.infraestructure.out.repository.CapacidadRepository;
import com.onclass.capacidad.infraestructure.out.repository.IBootcampCapacidadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CapacidadAdapter implements ICapacidadPersistencePort {

    private final CapacidadRepository capacidadRepository;
    private final CapacidadEntityMapper capacidadEntityMapper;
    private final IBootcampCapacidadRepository bootcampCapacidadRepository;
    private final BootcampCapacidadProjectionEntityMapper bootcampCapacidadProjectionEntityMapper;

    @Override
    public Mono<Capacidad> guardarCapacidad(Capacidad capacidad) {
        return Mono.just(capacidad)
                .map(capacidadEntityMapper::toCapacidadEntity)
                .flatMap(capacidadRepository::save)
                .map(capacidadEntity -> {
                    Capacidad capacidadDominio = capacidadEntityMapper.toCapacidad(capacidadEntity);
                    capacidadDominio.setTecnologias(capacidad.getTecnologias());
                    return capacidadDominio;
                });
    }

    @Override
    public Mono<PaginadoCustom<Capacidad>> listarCapacidadesPaginadas(int pagina, int tamanio, String direccionAscDesc) {
        Sort.Direction direccion = direccionAscDesc.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

        PageRequest pageRequest = PageRequest.of(pagina, tamanio, Sort.by(direccion, "nombre"));
        Mono<List<Capacidad>> capacidadesMono = capacidadRepository.findAllBy(pageRequest)
                .map(capacidadEntityMapper::toCapacidad)
                .collectList();

        Mono<Long> totalMono = capacidadRepository.count();

        return Mono.zip(capacidadesMono, totalMono)
                .map(tuple -> {
                    List<Capacidad> contenido = tuple.getT1();
                    Long totalElementos = tuple.getT2();
                    int totalPagina = (int) Math.ceil((double) totalElementos / tamanio);
                    return new PaginadoCustom<>(contenido, totalPagina, totalElementos);
                });
    }

    @Override
    public Flux<Capacidad> obtenerCapacidadesPorIds(List<Long> ids) {
        return capacidadRepository.findAllById(ids)
                .map(capacidadEntityMapper::toCapacidad);
    }

    @Override
    public Mono<Boolean> existenTodasLasCapacidades(List<Long> ids) {
        return capacidadRepository.countByIdIn(ids)
                .map(count -> count == ids.size());
    }

    @Override
    public Mono<Void> guardarRelacionBootcampCapacidad(Long idBootcamp, List<Long> capacidades) {
        return Flux.fromIterable(capacidades)
                .map(capacidad -> new BootcampCapacidadEntity(idBootcamp, capacidad))
                .flatMap(bootcampCapacidadRepository::save)
                .then();
    }

    @Override
    public Mono<PaginadoCustom<Long>> obtenerIdsBootcampsOrdenadosPorCantidad(int pagina, int tamanio, String filtro) {
        int offset = pagina * tamanio;
        Flux<Long> idsFlux = filtro.equalsIgnoreCase("asc") ?
                bootcampCapacidadRepository.findBootcampIdsOrderedByCountAsc(tamanio, offset) :
                bootcampCapacidadRepository.findBootcampIdsOrderedByCountDesc(tamanio, offset);

        return Mono.zip(idsFlux.collectList(), bootcampCapacidadRepository.countDistinctBootcamps())
                .map(tupla -> {
                    List<Long> ids = tupla.getT1();
                    Long totalElementos = tupla.getT2();
                    int totalPagina = (int) Math.ceil((double) totalElementos / tamanio);
                    return new PaginadoCustom<>(ids, totalPagina, totalElementos);
                });
    }

    @Override
    public Flux<BootcampCapacidadProjection> obtenerProyeccionesPorBootcamps(List<Long> idsBootcamp) {
        return bootcampCapacidadRepository.findCapacidadesByBootcampId(idsBootcamp)
                .map(bootcampCapacidadProjectionEntityMapper::toBootcampCapacidadProjection);
    }

    @Override
    public Flux<Long> findCapacidadesHuerfanas(List<Long> idsCapacidades) {
        if (idsCapacidades.isEmpty()) return Flux.empty();
        return bootcampCapacidadRepository.findCapacidadesHuerfanas(idsCapacidades);
    }

    @Override
    @Transactional
    public Mono<Void> eliminarRelacionesYCapacidadesHuerfanas(Long idBootcamp, List<Long> idsCapacidades) {
        return bootcampCapacidadRepository.deleteByIdBootcamp(idBootcamp)
                .then(idsCapacidades.isEmpty() ? Mono.empty() : capacidadRepository.deleteByIdIn(idsCapacidades));
    }

    @Override
    public Flux<Long> findCapacidadesPorBootcamp(Long idBootcamp) {
        return bootcampCapacidadRepository.findByIdBootcamp(idBootcamp)
                .map(BootcampCapacidadEntity::getIdCapacidad);
    }
}
