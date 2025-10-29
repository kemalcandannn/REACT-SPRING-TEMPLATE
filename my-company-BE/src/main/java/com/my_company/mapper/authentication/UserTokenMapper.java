package com.my_company.mapper.authentication;


import com.my_company.domain.dto.authentication.UserTokenDTO;
import com.my_company.domain.entity.authentication.UserToken;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserTokenMapper {
    UserTokenDTO entityToDto(UserToken userToken);

    UserToken dtoToEntity(UserTokenDTO userTokenDTO);

    List<UserTokenDTO> entityListToDtoList(List<UserToken> userTokenList);
}
