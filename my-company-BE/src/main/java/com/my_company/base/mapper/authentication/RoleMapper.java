package com.my_company.base.mapper.authentication;


import com.my_company.base.domain.dto.authentication.RoleDTO;
import com.my_company.base.domain.entity.authentication.Role;
import com.my_company.base.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper extends BaseMapper<Role, RoleDTO, String> {

}
