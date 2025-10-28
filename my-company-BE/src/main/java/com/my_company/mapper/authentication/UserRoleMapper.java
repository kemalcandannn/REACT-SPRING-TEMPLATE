package com.my_company.mapper.authentication;


import com.my_company.domain.dto.authentication.UserRoleDTO;
import com.my_company.domain.entity.authentication.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {
    @Mapping(source = "id.username", target = "username")
    @Mapping(source = "id.roleCode", target = "roleCode")
    UserRoleDTO entityToDto(UserRole userRole);

    @Mapping(source = "username", target = "id.username")
    @Mapping(source = "roleCode", target = "id.roleCode")
    UserRole dtoToEntity(UserRoleDTO userRoleDTO);

    List<UserRoleDTO> entityListToDtoList(List<UserRole> userRoleList);
}
