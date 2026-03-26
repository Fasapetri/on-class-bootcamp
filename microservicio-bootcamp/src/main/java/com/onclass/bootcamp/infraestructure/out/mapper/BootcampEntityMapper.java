package com.onclass.bootcamp.infraestructure.out.mapper;

import com.onclass.bootcamp.domain.model.Bootcamp;
import com.onclass.bootcamp.infraestructure.out.entity.BootcampEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
unmappedSourcePolicy = ReportingPolicy.IGNORE,
unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BootcampEntityMapper {

    BootcampEntity bootcampToBootcampEntity(Bootcamp bootcamp);
    Bootcamp bootcampEntityToBootcamp(BootcampEntity bootcampEntity);
}
