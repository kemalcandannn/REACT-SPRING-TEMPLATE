package com.my_company.mapper.authentication;


import com.my_company.domain.dto.authentication.UserDTO;
import com.my_company.domain.entity.authentication.User;
import com.my_company.domain.request.authentication.SignUpRequest;
import com.my_company.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<User, UserDTO, String> {

    UserDTO signUpRequestToDTO(SignUpRequest request);
}
