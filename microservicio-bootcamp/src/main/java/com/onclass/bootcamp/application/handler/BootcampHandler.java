package com.onclass.bootcamp.application.handler;

import com.onclass.bootcamp.application.dto.BootcampRequest;
import com.onclass.bootcamp.application.dto.BootcampResponse;
import com.onclass.bootcamp.application.mapper.IBootcampMapper;
import com.onclass.bootcamp.domain.api.IBootcampServicePort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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

}
