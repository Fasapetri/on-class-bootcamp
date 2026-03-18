package com.onclass.capacidad.infraestructure.out.adapter;

import com.onclass.capacidad.domain.model.Capacidad;
import com.onclass.capacidad.domain.model.PaginadoCustom;
import com.onclass.capacidad.domain.spi.ICapacidadPersistencePort;
import com.onclass.capacidad.infraestructure.out.mapper.CapacidadEntityMapper;
import com.onclass.capacidad.infraestructure.out.repository.CapacidadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CapacidadAdapter implements ICapacidadPersistencePort {

    private final CapacidadRepository capacidadRepository;
    private final CapacidadEntityMapper capacidadEntityMapper;

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
}
