package com.my_company.service.authentication;

import com.my_company.cache.ParameterCache;
import com.my_company.constants.TextConstants;
import com.my_company.constants.enums.ErrorCode;
import com.my_company.constants.enums.ParameterCode;
import com.my_company.domain.dto.authentication.UserDTO;
import com.my_company.domain.entity.authentication.User;
import com.my_company.exception.UserAuthenticationException;
import com.my_company.mapper.authentication.UserMapper;
import com.my_company.repository.authentication.UserRepository;
import com.my_company.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService extends BaseService<User, UserDTO, String> {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, UserMapper mapper, PasswordEncoder passwordEncoder) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    public User findAuthenticationUserByUsername(String username) {
        return repository.findById(username)
                .orElseThrow(() -> new UserAuthenticationException(
                        ErrorCode.INCORRECT_USERNAME_OR_PASSWORD,
                        TextConstants.INCORRECT_USERNAME_OR_PASSWORD_MESSAGE));
    }

    public void changePassword(User user, String password) {
        Integer passwordExpirationDays = ParameterCache.getParamValueAsIntegerWithControl(ParameterCode.PASSWORD_EXPIRATION_CONTROL, ParameterCode.PASSWORD_EXPIRATION_DAYS);

        mapper.passwordChanged(passwordEncoder, user, password, passwordExpirationDays);
        repository.save(user);
    }
}
