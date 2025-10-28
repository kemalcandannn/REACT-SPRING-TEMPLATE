package com.my_company.base.mapper.authentication;


import com.my_company.base.domain.dto.authentication.RoleMenuDTO;
import com.my_company.base.domain.entity.authentication.RoleMenu;
import com.my_company.base.domain.entity.authentication.RoleMenuId;
import com.my_company.base.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMenuMapper extends BaseMapper<RoleMenu, RoleMenuDTO, RoleMenuId> {

    @Override
    @Mapping(source = "id.roleCode", target = "roleCode")
    @Mapping(source = "id.menuCode", target = "menuCode")
    RoleMenuDTO entityToDto(RoleMenu entity);

    @Override
    @Mapping(source = "roleCode", target = "id.roleCode")
    @Mapping(source = "menuCode", target = "id.menuCode")
    RoleMenu dtoToEntity(RoleMenuDTO roleMenuDTO);
}
