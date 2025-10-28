package com.my_company.mapper;


import com.my_company.domain.entity.BaseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BaseMapper<ENTITY extends BaseEntity<ID>, DTO, ID> {

    DTO entityToDto(ENTITY entity);

    ENTITY dtoToEntity(DTO dto);

    List<DTO> entityListToDtoList(List<ENTITY> entityList);

    List<ENTITY> dtoListToEntityList(List<DTO> dtoList);

    default LocalDate getLocalDateNow() {
        return LocalDate.now();
    }

    default LocalDateTime getLocalDateTimeNow() {
        return LocalDateTime.now();
    }
}
