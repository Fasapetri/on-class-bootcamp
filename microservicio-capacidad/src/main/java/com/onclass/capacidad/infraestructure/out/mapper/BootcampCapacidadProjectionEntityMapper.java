package com.onclass.capacidad.infraestructure.out.mapper;

import com.onclass.capacidad.domain.model.BootcampCapacidadProjection;
import com.onclass.capacidad.infraestructure.out.entity.BootcampCapacidadProjectionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
unmappedSourcePolicy = ReportingPolicy.IGNORE,
unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BootcampCapacidadProjectionEntityMapper {

    BootcampCapacidadProjectionEntity toBootcampCapacidadProjectionEntity(BootcampCapacidadProjection bootcampCapacidadProjection);

    BootcampCapacidadProjection toBootcampCapacidadProjection(BootcampCapacidadProjectionEntity bootcampCapacidadProjectionEntity);
}
