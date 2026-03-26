package com.onclass.bootcamp.application.handler;

import com.onclass.bootcamp.application.dto.BootcampRequest;
import com.onclass.bootcamp.application.dto.BootcampResponse;
import reactor.core.publisher.Mono;

public interface IBootcampHandler {

    Mono<BootcampResponse> guardarBootcamp(BootcampRequest bootcampRequest);
}
