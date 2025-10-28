package com.my_company.base.mapper.authentication;


import com.my_company.base.domain.dto.authentication.MenuDTO;
import com.my_company.base.domain.entity.authentication.Menu;
import com.my_company.base.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MenuMapper extends BaseMapper<Menu, MenuDTO, String> {

}
