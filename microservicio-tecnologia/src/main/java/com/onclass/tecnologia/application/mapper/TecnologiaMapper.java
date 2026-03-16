package com.onclass.tecnologia.application.mapper;

import com.onclass.tecnologia.application.dto.TecnologiaRequest;
import com.onclass.tecnologia.application.dto.TecnologiaResponse;
import com.onclass.tecnologia.domain.model.Tecnologia;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
unmappedSourcePolicy = ReportingPolicy.IGNORE,
unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TecnologiaMapper {

    TecnologiaMapper INSTANCE = Mappers.getMapper(TecnologiaMapper.class);

    Tecnologia tecnologiaRequestToTecnologia(TecnologiaRequest tecnologiaRequest);
    TecnologiaResponse tecnologiaToTecnologiaResponse(Tecnologia tecnologia);
}
