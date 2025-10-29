package com.my_company.mapper.authentication;


import com.my_company.domain.dto.authentication.UserDTO;
import com.my_company.domain.entity.authentication.User;
import com.my_company.domain.request.authentication.ChangePasswordRequest;
import com.my_company.domain.request.authentication.SignUpRequest;
import com.my_company.domain.response.authentication.UserResponse;
import com.my_company.mapper.BaseMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<User, UserDTO, String> {
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(request.getPassword()))")
    @Mapping(target = "passwordValidUntil", expression = "java(passwordExpirationDays == null ? null : getLocalDateTimeNow().plusDays(passwordExpirationDays))")
    @Mapping(target = "provider", constant = "LOCAL")
    @Mapping(target = "status", constant = "PASSIVE")
    @Mapping(target = "createdAt", expression = "java(getLocalDateTimeNow())")
    UserDTO signUpRequestToDTO(SignUpRequest request, @Context PasswordEncoder passwordEncoder, Integer passwordExpirationDays);

    @Mapping(target = "password3", expression = "java(user.getPassword2())")
    @Mapping(target = "password2", expression = "java(user.getPassword())")
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(request.getNewPassword()))")
    @Mapping(target = "passwordValidUntil", expression = "java(passwordExpirationDays == null ? null : getLocalDateTimeNow().plusDays(passwordExpirationDays))")
    void passwordChanged(@Context PasswordEncoder passwordEncoder, @MappingTarget User user, ChangePasswordRequest request, Integer passwordExpirationDays);

    UserResponse entityToResponse(User user, List<String> roleList, List<String> menuList);
}
