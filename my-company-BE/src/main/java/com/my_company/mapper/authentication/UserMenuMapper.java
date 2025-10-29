package com.my_company.mapper.authentication;

import com.my_company.domain.dto.authentication.UserMenuDTO;
import com.my_company.domain.entity.authentication.UserMenu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMenuMapper {
    @Mapping(source = "id.username", target = "username")
    @Mapping(source = "id.menuCode", target = "menuCode")
    UserMenuDTO entityToDto(UserMenu userMenu);

    @Mapping(source = "username", target = "id.username")
    @Mapping(source = "menuCode", target = "id.menuCode")
    UserMenu dtoToEntity(UserMenuDTO userMenuDTO);

    List<UserMenuDTO> entityListToDtoList(List<UserMenu> userMenuList);
}
