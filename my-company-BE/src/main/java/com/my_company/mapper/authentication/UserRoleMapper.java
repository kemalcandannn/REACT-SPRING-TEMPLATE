package com.my_company.mapper.authentication;


import com.my_company.domain.dto.authentication.UserRoleDTO;
import com.my_company.domain.entity.authentication.UserRole;
import com.my_company.domain.entity.authentication.UserRoleId;
import com.my_company.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserRoleMapper extends BaseMapper<UserRole, UserRoleDTO, UserRoleId> {

    @Override
    @Mapping(source = "id.username", target = "username")
    @Mapping(source = "id.roleCode", target = "roleCode")
    UserRoleDTO entityToDto(UserRole entity);

    @Override
    @Mapping(source = "username", target = "id.username")
    @Mapping(source = "roleCode", target = "id.roleCode")
    UserRole dtoToEntity(UserRoleDTO userRoleDTO);
}
