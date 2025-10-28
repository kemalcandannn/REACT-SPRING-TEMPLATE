package com.my_company.mapper.system;


import com.my_company.domain.dto.system.ParametersDTO;
import com.my_company.domain.entity.system.Parameters;
import com.my_company.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParametersMapper extends BaseMapper<Parameters, ParametersDTO, String> {
}
