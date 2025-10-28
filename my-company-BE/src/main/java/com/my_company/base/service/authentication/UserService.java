package com.my_company.base.service.authentication;

import com.my_company.base.constants.TextConstants;
import com.my_company.base.constants.enums.ErrorCode;
import com.my_company.base.domain.dto.authentication.UserDTO;
import com.my_company.base.domain.entity.authentication.User;
import com.my_company.base.exception.UserAuthenticationException;
import com.my_company.base.mapper.authentication.UserMapper;
import com.my_company.base.repository.authentication.UserRepository;
import com.my_company.base.service.BaseService;
import com.my_company.base.utils.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService extends BaseService<User, UserDTO, String> {
    private final UserRepository repository;
    private final UserMapper mapper;

    public UserService(UserRepository repository, UserMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    public UserDTO findByUsername(String username) {
        List<User> userList = repository.findByUsername(username);
        return CollectionUtils.isEmpty(userList) ? null : mapper.entityToDto(userList.get(0));
    }

    public User findAuthenticationUserByUsername(String username) {
        List<User> userList = repository.findByUsername(username);
        if (CollectionUtils.isEmpty(userList)) {
            throw new UserAuthenticationException(ErrorCode.INCORRECT_USERNAME_OR_PASSWORD, TextConstants.INCORRECT_USERNAME_OR_PASSWORD_MESSAGE);
        }

        return userList.get(0);
    }
}
