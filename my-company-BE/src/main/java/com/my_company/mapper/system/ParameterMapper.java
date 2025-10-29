package com.my_company.mapper.system;


import com.my_company.domain.dto.system.ParameterDTO;
import com.my_company.domain.entity.system.Parameter;
import com.my_company.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParameterMapper extends BaseMapper<Parameter, ParameterDTO, String> {
}
