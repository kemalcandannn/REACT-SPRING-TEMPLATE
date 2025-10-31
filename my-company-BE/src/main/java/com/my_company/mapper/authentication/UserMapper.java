package com.my_company.mapper.authentication;


import com.my_company.constants.enums.Language;
import com.my_company.domain.dto.authentication.UserDTO;
import com.my_company.domain.entity.authentication.User;
import com.my_company.domain.response.authentication.UserResponse;
import com.my_company.mapper.BaseMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<User, UserDTO, String> {
    @Mapping(target = "passwordValidUntil", expression = "java(passwordExpirationDays == null ? null : getLocalDateTimeNow().plusDays(passwordExpirationDays))")
    @Mapping(target = "provider", constant = "LOCAL")
    @Mapping(target = "status", constant = "PASSIVE")
    @Mapping(target = "createdAt", expression = "java(getLocalDateTimeNow())")
    UserDTO registerRequestToDTO(String username, String password, Integer passwordExpirationDays, Language language);

    @Mapping(target = "provider", constant = "GOOGLE")
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "createdAt", expression = "java(getLocalDateTimeNow())")
    UserDTO createGoogleUserDTO(String username, String password, String providerId, Language language);

    @Mapping(target = "password3", expression = "java(user.getPassword2())")
    @Mapping(target = "password2", expression = "java(user.getPassword())")
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(password))")
    @Mapping(target = "passwordValidUntil", expression = "java(passwordExpirationDays == null ? null : getLocalDateTimeNow().plusDays(passwordExpirationDays))")
    void passwordChanged(@Context PasswordEncoder passwordEncoder, @MappingTarget User user, String password, Integer passwordExpirationDays);

    UserResponse entityToResponse(User user, List<String> roleList, List<String> menuList);
}
