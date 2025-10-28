package com.my_company.mapper.authentication;

import com.my_company.domain.dto.authentication.RoleMenuDTO;
import com.my_company.domain.entity.authentication.RoleMenu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMenuMapper {
    @Mapping(source = "id.roleCode", target = "roleCode")
    @Mapping(source = "id.menuCode", target = "menuCode")
    RoleMenuDTO entityToDto(RoleMenu roleMenu);

    @Mapping(source = "roleCode", target = "id.roleCode")
    @Mapping(source = "menuCode", target = "id.menuCode")
    RoleMenu dtoToEntity(RoleMenuDTO roleMenuDTO);

    List<RoleMenuDTO> entityListToDtoList(List<RoleMenu> roleMenuList);
}
