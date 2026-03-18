package com.onclass.capacidad.application.mapper;

import com.onclass.capacidad.application.dto.CapacidadRequest;
import com.onclass.capacidad.application.dto.CapacidadResponse;
import com.onclass.capacidad.domain.model.Capacidad;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CapacidadMapper {

    CapacidadResponse toCapacidadResponse(Capacidad capacidad);

    Capacidad toCapacidad(CapacidadRequest request);
}
