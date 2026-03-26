package com.onclass.capacidad.application.handler;

import com.onclass.capacidad.application.dto.CapacidadRequest;
import com.onclass.capacidad.application.dto.CapacidadResponse;
import com.onclass.capacidad.application.mapper.CapacidadMapper;
import com.onclass.capacidad.domain.api.ICapacidadServicePort;
import com.onclass.capacidad.domain.model.CapacidadDetalle;
import com.onclass.capacidad.domain.model.PaginadoCustom;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class CapacidadHandler implements ICapacidadHandler{

    private final ICapacidadServicePort capacidadServicePort;
    private final CapacidadMapper capacidadMapper;

    public CapacidadHandler(ICapacidadServicePort capacidadServicePort, CapacidadMapper capacidadMapper) {
        this.capacidadServicePort = capacidadServicePort;
        this.capacidadMapper = capacidadMapper;
    }

    @Override
    public Mono<CapacidadResponse> guardarCapacidad(CapacidadRequest request) {
        return Mono.just(request)
                .map(capacidadMapper::toCapacidad)
                .flatMap(capacidadServicePort::guardarCapacidad)
                .map(capacidadMapper::toCapacidadResponse);
    }

    @Override
    public Mono<PaginadoCustom<CapacidadDetalle>> listarCapacidades(int pagina, int tamanio, String ordenarPor, String direccion) {
        return capacidadServicePort.listarCapacidades(pagina, tamanio, ordenarPor, direccion);

    }

    @Override
    public Mono<Boolean> existenTodasLasCapacidades(List<Long> ids) {
        return capacidadServicePort.existenTodasLasCapacidades(ids);
    }

    @Override
    public Mono<Void> guardarRelacionBootcampCapacidad(Long idBootcamp, List<Long> capacidades) {
        return capacidadServicePort.guardarRelacionBootcampCapacidad(idBootcamp, capacidades);
    }

    @Override
    public Mono<PaginadoCustom<Long>> obtenerIdsBootcampsOrdenados(int pagina, int tamanio, String filtro) {
        return capacidadServicePort.obtenerIdsBootcampsOrdenadosPorCantidad(pagina, tamanio, filtro);
    }

    @Override
    public Mono<Map<Long, List<CapacidadDetalle>>> obtenerCapacidadesPorBootcamps(List<Long> idsBootcamp) {
        return capacidadServicePort.obtenerCapacidadesPorBootcamps(idsBootcamp);
    }

    @Override
    public Mono<Void> eliminarRelacionesYCapacidadesHuerfanas(Long idBootcamp) {
        return capacidadServicePort.eliminarRelacionesYCapacidadesHuerfanas(idBootcamp);
    }


}
