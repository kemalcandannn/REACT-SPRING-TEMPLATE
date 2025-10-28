package com.my_company.base.mapper.authentication;


import com.my_company.base.domain.dto.authentication.UserDTO;
import com.my_company.base.domain.entity.authentication.User;
import com.my_company.base.domain.request.authentication.SignUpRequest;
import com.my_company.base.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<User, UserDTO, String> {

    UserDTO signUpRequestToDTO(SignUpRequest request);
}
