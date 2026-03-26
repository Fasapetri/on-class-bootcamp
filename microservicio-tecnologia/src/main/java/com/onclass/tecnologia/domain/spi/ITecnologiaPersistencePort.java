package com.onclass.tecnologia.domain.spi;

import com.onclass.tecnologia.domain.model.PaginaCustom;
import com.onclass.tecnologia.domain.model.Tecnologia;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface ITecnologiaPersistencePort {

    Mono<Tecnologia> guardarTecnologia(Tecnologia tecnologia);
    Mono<Boolean> existeTecnologiaPorNombre(String nombreTecnologia);
    Mono<Boolean> existenTodasLasTecnologias(List<Long> idsTecnologia);
    Mono<Void> guardarRelacionCapacidadTecnologia(Long idCapacidad, List<Long> tecnologias);
    Mono<Map<Long, List<Tecnologia>>> obtenerTecnologiasPorCapacidades(List<Long> idsCapacidad);
    Mono<PaginaCustom<Long>> obtenerCapacidadesOrdenadasPorCantidad(int pagina, int tamanio, String direccion);
    Mono<Void> eliminarRelacionesYTecnologiasHuerfanas(List<Long> idsCapacidades);
}
