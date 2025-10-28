package com.my_company.service.authentication;

import com.my_company.constants.TextConstants;
import com.my_company.constants.enums.ErrorCode;
import com.my_company.domain.dto.authentication.UserDTO;
import com.my_company.domain.entity.authentication.User;
import com.my_company.exception.UserAuthenticationException;
import com.my_company.mapper.authentication.UserMapper;
import com.my_company.repository.authentication.UserRepository;
import com.my_company.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService extends BaseService<User, UserDTO, String> {
    private final UserRepository repository;

    public UserService(UserRepository repository, UserMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
    }

    public User findAuthenticationUserByUsername(String username) {
        return repository.findById(username)
                .orElseThrow(() -> new UserAuthenticationException(
                        ErrorCode.INCORRECT_USERNAME_OR_PASSWORD,
                        TextConstants.INCORRECT_USERNAME_OR_PASSWORD_MESSAGE));
    }
}
