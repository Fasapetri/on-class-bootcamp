package com.onclass.persona.domain.spi;

import com.onclass.persona.domain.model.BootcampFecha;
import reactor.core.publisher.Flux;

import java.util.List;

public interface IBootcampExternalPort {

    Flux<BootcampFecha> obtenerFechasBootcamps(List<Long> idsBootcamps);
}
