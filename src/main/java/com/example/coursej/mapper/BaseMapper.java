package com.example.coursej.mapper;

import java.util.List;

public interface BaseMapper<ENTITY, DTO> {

    DTO toDTO(ENTITY entity);
    ENTITY toEntity(DTO dto);
    List<DTO> toDTOList(List<ENTITY> entityList);
    List<ENTITY> toEntityList(List<DTO> dtoList);

}
