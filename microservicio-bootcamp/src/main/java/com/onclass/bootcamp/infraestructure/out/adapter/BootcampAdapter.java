package com.onclass.bootcamp.infraestructure.out.adapter;

import com.onclass.bootcamp.domain.model.Bootcamp;
import com.onclass.bootcamp.domain.model.PaginaCustom;
import com.onclass.bootcamp.domain.spi.IBootcampPersistencePort;
import com.onclass.bootcamp.infraestructure.out.mapper.BootcampEntityMapper;
import com.onclass.bootcamp.infraestructure.out.repository.IBootcampRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BootcampAdapter implements IBootcampPersistencePort {

    private final IBootcampRepository bootcampRepository;
    private final BootcampEntityMapper bootcampEntityMapper;

    @Override
    public Mono<Bootcamp> guardarBootcamp(Bootcamp bootcamp) {
        return Mono.just(bootcamp)
                .map(bootcampEntityMapper::bootcampToBootcampEntity)
                .flatMap(bootcampRepository::save)
                .map(bootcampEnitityGuardada -> {
                    Bootcamp bootcampDominio = bootcampEntityMapper.bootcampEntityToBootcamp(bootcampEnitityGuardada);
                    bootcampDominio.setCapacidades(bootcamp.getCapacidades());
                    return bootcampDominio;
                });
    }

    @Override
    public Mono<Boolean> existeBootcampPorNombre(String nombreBootcamp) {
        return bootcampRepository.existsByNombre(nombreBootcamp);
    }

    @Override
    public Mono<PaginaCustom<Bootcamp>> listarBootcampsPaginados(int numeroPagina, int tamanioPagina, String orden) {
        Sort.Direction direction = orden.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        PageRequest pageRequest = PageRequest.of(numeroPagina, tamanioPagina, Sort.by(direction, "nombre"));

        Mono<List<Bootcamp>> bootcampsMono = bootcampRepository.findAllBy(pageRequest)
                .map(bootcampEntityMapper::bootcampEntityToBootcamp)
                .collectList();

        Mono<Long> totalMono = bootcampRepository.count();

        return Mono.zip(bootcampsMono, totalMono)
                .map(tupla -> {
                    List<Bootcamp> contenido = tupla.getT1();
                    Long totalElementos = tupla.getT2();
                    int totalPaginas = (int) Math.ceil((double) totalElementos / tamanioPagina);

                    return new PaginaCustom<>(contenido, totalPaginas, totalElementos);
                });
    }

    @Override
    public Flux<Bootcamp> obtenerBootcampsPorIds(List<Long> ids) {
        return bootcampRepository.findAllById(ids)
                .map(bootcampEntityMapper::bootcampEntityToBootcamp);
    }
}
