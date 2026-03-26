package com.onclass.persona.infraestructure.out.adapter;

import com.onclass.persona.domain.model.BootcampFecha;
import com.onclass.persona.domain.spi.IBootcampExternalPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BootcampExternalAdapter implements IBootcampExternalPort {

    @Override
    public Flux<BootcampFecha> obtenerFechasBootcamps(List<Long> idsBootcamps) {
        return null;
    }
}
