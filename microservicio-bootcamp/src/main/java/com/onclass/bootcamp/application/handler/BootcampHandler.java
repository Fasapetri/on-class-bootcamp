package com.onclass.bootcamp.application.handler;

import com.onclass.bootcamp.application.dto.BootcampRequest;
import com.onclass.bootcamp.application.dto.BootcampResponse;
import com.onclass.bootcamp.application.mapper.IBootcampMapper;
import com.onclass.bootcamp.domain.api.IBootcampServicePort;
import com.onclass.bootcamp.domain.model.Bootcamp;
import com.onclass.bootcamp.domain.model.BootcampDetalle;
import com.onclass.bootcamp.domain.model.PaginaCustom;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class BootcampHandler implements IBootcampHandler{

    private final IBootcampServicePort bootcampServicePort;
    private final IBootcampMapper bootcampMapper;

    public BootcampHandler(IBootcampServicePort bootcampServicePort, IBootcampMapper bootcampMapper) {
        this.bootcampServicePort = bootcampServicePort;
        this.bootcampMapper = bootcampMapper;
    }

    @Override
    public Mono<BootcampResponse> guardarBootcamp(BootcampRequest bootcampRequest) {
        return Mono.just(bootcampRequest)
                .map(bootcampMapper::bootcampRequestToBootcamp)
                .flatMap(bootcampServicePort::guardarBootcamp)
                .map(bootcampMapper::bootcampToBootcampResponse);
    }

    @Override
    public Mono<PaginaCustom<BootcampDetalle>> listarBootcamps(int pagina, int tamanio, String orden, String filtro) {
        return bootcampServicePort.buscarBootcamps(pagina, tamanio, orden, filtro);
    }

    @Override
    public Mono<Void> eliminarBootcamp(Long idBootcamp) {
        return bootcampServicePort.eliminarBootcamp(idBootcamp);
    }

    @Override
    public Flux<Bootcamp> buscarBootcampsPorIds(List<Long> idsBootcamps) {
        return bootcampServicePort.buscarBootcampsPorIds(idsBootcamps);
    }

}
