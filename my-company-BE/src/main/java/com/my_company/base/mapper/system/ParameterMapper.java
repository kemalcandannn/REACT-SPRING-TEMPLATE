package com.my_company.base.mapper.system;


import com.my_company.base.domain.dto.system.ParameterDTO;
import com.my_company.base.domain.entity.system.Parameter;
import com.my_company.base.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParameterMapper extends BaseMapper<Parameter, ParameterDTO, String> {

}
