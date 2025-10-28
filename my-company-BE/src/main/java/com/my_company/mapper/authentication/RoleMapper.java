package com.my_company.mapper.authentication;

import com.my_company.domain.dto.authentication.RoleDTO;
import com.my_company.domain.entity.authentication.Role;
import com.my_company.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper extends BaseMapper<Role, RoleDTO, String> {
}
