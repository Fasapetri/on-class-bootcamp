package com.onclass.tecnologia.infraestructure.out.mapper;

import com.onclass.tecnologia.domain.model.Tecnologia;
import com.onclass.tecnologia.infraestructure.out.entity.TecnologiaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TecnologiaEntityMapper {

    TecnologiaEntity tecnologiaToTecnologiaEntity(Tecnologia tecnologia);

    Tecnologia tecnologiaEntityToTecnologia(TecnologiaEntity tecnologiaEntity);
}
