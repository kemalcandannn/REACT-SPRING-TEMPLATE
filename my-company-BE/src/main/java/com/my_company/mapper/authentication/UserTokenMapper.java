package com.my_company.mapper.authentication;


import com.my_company.domain.dto.authentication.UserDTO;
import com.my_company.domain.dto.authentication.UserTokenDTO;
import com.my_company.domain.entity.authentication.UserToken;
import com.my_company.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.Objects;

@Mapper(componentModel = "spring")
public interface UserTokenMapper extends BaseMapper<UserToken, UserTokenDTO, Long> {

    @Mapping(target = "type", constant = "PASSWORD_RESET")
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "expiresAt", expression = "java(getExpiresAt(tokenExpirationMinutes))")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    UserTokenDTO extraxtPasswordResetUserTokenDTO(UserDTO userDTO, String token, Integer tokenExpirationMinutes);

    @Mapping(target = "type", constant = "ACCOUNT_VERIFICATION")
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "expiresAt", expression = "java(getExpiresAt(tokenExpirationMinutes))")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    UserTokenDTO extraxtAccountVerificationUserTokenDTO(UserDTO userDTO, String token, Integer tokenExpirationMinutes);

    default LocalDateTime getExpiresAt(Integer tokenExpirationMinutes) {
        return Objects.isNull(tokenExpirationMinutes) ? null : LocalDateTime.now().plusMinutes(tokenExpirationMinutes);
    }
}
