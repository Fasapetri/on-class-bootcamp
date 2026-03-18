package com.onclass.capacidad.domain.api;

import com.onclass.capacidad.domain.model.Capacidad;
import com.onclass.capacidad.domain.model.CapacidadDetalle;
import com.onclass.capacidad.domain.model.PaginadoCustom;
import reactor.core.publisher.Mono;

public interface ICapacidadServicePort {

    Mono<Capacidad> guardarCapacidad(Capacidad capacidad);
    Mono<PaginadoCustom<CapacidadDetalle>> listarCapacidades(int pagina, int tamanio, String ordenarPor, String direccion);
}
