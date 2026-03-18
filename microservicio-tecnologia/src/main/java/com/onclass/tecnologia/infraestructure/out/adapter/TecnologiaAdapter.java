package com.onclass.tecnologia.infraestructure.out.adapter;

import com.onclass.tecnologia.domain.model.PaginaCustom;
import com.onclass.tecnologia.domain.model.Tecnologia;
import com.onclass.tecnologia.domain.spi.ITecnologiaPersistencePort;
import com.onclass.tecnologia.infraestructure.out.entity.CapacidadTecnologiaEntity;
import com.onclass.tecnologia.infraestructure.out.entity.CapacidadTecnologiaProjection;
import com.onclass.tecnologia.infraestructure.out.mapper.TecnologiaEntityMapper;
import com.onclass.tecnologia.infraestructure.out.repository.ICapacidadTecnologiaRepository;
import com.onclass.tecnologia.infraestructure.out.repository.ITecnologiaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TecnologiaAdapter implements ITecnologiaPersistencePort {

    private final ITecnologiaRepository tecnologiaRepository;
    private final TecnologiaEntityMapper tecnologiaEntityMapper;
    private final ICapacidadTecnologiaRepository capacidadTecnologiaRepository;

    @Override
    public Mono<Tecnologia> guardarTecnologia(Tecnologia tecnologia) {
        return Mono.just(tecnologia)
                .map(tecnologiaEntityMapper::tecnologiaToTecnologiaEntity)
                .flatMap(tecnologiaRepository::save)
                .map(tecnologiaEntityMapper::tecnologiaEntityToTecnologia);
    }

    @Override
    public Mono<Boolean> existeTecnologiaPorNombre(String nombreTecnologia) {
        return tecnologiaRepository.existsByNombre(nombreTecnologia);
    }

    @Override
    public Mono<Boolean> existenTodasLasTecnologias(List<Long> idsTecnologia) {
        return tecnologiaRepository.countByIdIn(idsTecnologia)
                .map(count -> count == idsTecnologia.size());
    }

    @Override
    public Mono<Void> guardarRelacionCapacidadTecnologia(Long idCapacidad, List<Long> tecnologias) {
        return Flux.fromIterable(tecnologias)
                .map(idTecnologia -> new CapacidadTecnologiaEntity(idCapacidad, idTecnologia))
                .flatMap(capacidadTecnologiaRepository::save)
                .then();
    }

    @Override
    public Mono<Map<Long, List<Tecnologia>>> obtenerTecnologiasPorCapacidades(List<Long> idsCapacidad) {
        if(idsCapacidad == null || idsCapacidad.isEmpty()){
            return Mono.just(Map.of());
        }

        return capacidadTecnologiaRepository.findTecnologiasByCapacidadIds(idsCapacidad)
                .collectList()
                .map(projection -> projection.stream()
                        .collect(Collectors.groupingBy(
                                CapacidadTecnologiaProjection::getIdCapacidad,
                                Collectors.mapping(
                                        p -> new Tecnologia(p.getIdTecnologia(), p.getNombreTecnologia(), ""),
                                        Collectors.toList()
                                )
                        ))

                );
    }

    @Override
    public Mono<PaginaCustom<Long>> obtenerCapacidadesOrdenadasPorCantidad(int pagina, int tamanio, String direccion) {
        int offset = pagina * tamanio;

        Flux<Long> idsFlux = direccion.equalsIgnoreCase("asc") ?
                capacidadTecnologiaRepository.findCapacidadIdsOrderedByCountAsc(tamanio, offset) :
                capacidadTecnologiaRepository.findCapacidadIdsOrderedByCountDesc(tamanio, offset);

        return Mono.zip(idsFlux.collectList(), capacidadTecnologiaRepository.countDistinctCapacidades())
                .map(tuple -> {
                    List<Long> ids = tuple.getT1();
                    Long totalElementos = tuple.getT2();
                    int totalPaginas = (int) Math.ceil((double) totalElementos / tamanio);
                    return new PaginaCustom<>(ids, totalPaginas, totalElementos);
                });
    }
}
