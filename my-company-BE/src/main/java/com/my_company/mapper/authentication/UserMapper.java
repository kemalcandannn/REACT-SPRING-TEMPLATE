package com.my_company.mapper.authentication;


import com.my_company.domain.dto.authentication.UserDTO;
import com.my_company.domain.entity.authentication.User;
import com.my_company.domain.request.authentication.SignUpRequest;
import com.my_company.domain.response.authentication.UserResponse;
import com.my_company.mapper.BaseMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<User, UserDTO, String> {
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(request.getPassword()))")
    @Mapping(target = "passwordValidUntil", expression = "java(getLocalDateTimeNow().plusDays(passwordExpirationDays))")
    @Mapping(target = "provider", constant = "LOCAL")
    @Mapping(target = "status", constant = "PASSIVE")
    @Mapping(target = "createdAt", expression = "java(getLocalDateTimeNow())")
    UserDTO signUpRequestToDTO(SignUpRequest request, @Context PasswordEncoder passwordEncoder, int passwordExpirationDays);

    UserResponse entityToResponse(User user, List<SimpleGrantedAuthority> authorities);
}
