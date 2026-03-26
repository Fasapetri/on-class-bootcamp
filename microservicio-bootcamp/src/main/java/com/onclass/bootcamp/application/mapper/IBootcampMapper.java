package com.onclass.bootcamp.application.mapper;

import com.onclass.bootcamp.application.dto.BootcampRequest;
import com.onclass.bootcamp.application.dto.BootcampResponse;
import com.onclass.bootcamp.domain.model.Bootcamp;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
unmappedSourcePolicy = ReportingPolicy.IGNORE,
unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IBootcampMapper {

    BootcampResponse bootcampToBootcampResponse(Bootcamp bootcamp);
    Bootcamp bootcampRequestToBootcamp(BootcampRequest bootcampRequest);
}
