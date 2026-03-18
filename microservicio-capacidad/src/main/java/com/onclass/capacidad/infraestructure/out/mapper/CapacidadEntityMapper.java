package com.onclass.capacidad.infraestructure.out.mapper;


import com.onclass.capacidad.domain.model.Capacidad;
import com.onclass.capacidad.infraestructure.out.entity.CapacidadEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CapacidadEntityMapper {

    CapacidadEntity toCapacidadEntity(Capacidad capacidad);

    Capacidad toCapacidad(CapacidadEntity entity);
}
