package com.my_company.mapper.authentication;


import com.my_company.domain.dto.authentication.MenuDTO;
import com.my_company.domain.entity.authentication.Menu;
import com.my_company.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MenuMapper extends BaseMapper<Menu, MenuDTO, String> {

}
