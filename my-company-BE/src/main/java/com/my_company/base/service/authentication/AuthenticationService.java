package com.my_company.base.service.authentication;

import com.my_company.base.constants.ApplicationConstants;
import com.my_company.base.constants.TextConstants;
import com.my_company.base.constants.enums.AuthProvider;
import com.my_company.base.constants.enums.ErrorCode;
import com.my_company.base.constants.enums.ParameterCode;
import com.my_company.base.constants.enums.Status;
import com.my_company.base.domain.dto.authentication.UserDTO;
import com.my_company.base.domain.entity.authentication.User;
import com.my_company.base.domain.request.authentication.LoginRequest;
import com.my_company.base.domain.request.authentication.SignUpRequest;
import com.my_company.base.domain.response.authentication.LoginResponse;
import com.my_company.base.exception.BadRequestException;
import com.my_company.base.exception.UserAuthenticationException;
import com.my_company.base.mapper.authentication.UserMapper;
import com.my_company.base.service.system.ParameterService;
import com.my_company.base.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final UserMapper userMapper;
    private final ParameterService parameterService;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse localSignUp(SignUpRequest request) {
        signUpValidation(request);

        UserDTO userDTO = userMapper.signUpRequestToDTO(request);
        userDTO.setPassword(passwordEncoder.encode(request.getPassword()));
        userDTO.setProvider(AuthProvider.LOCAL);
        userDTO.setCreatedAt(LocalDateTime.now());
        userDTO = userService.saveOrUpdate(userDTO);

        return authenticate(
                LoginRequest
                        .builder()
                        .username(userDTO.getUsername())
                        .password(request.getPassword())
                        .build()
        );
    }

    public LoginResponse authenticate(LoginRequest request) {
        User user = userService.findAuthenticationUserByUsername(request.getUsername());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserAuthenticationException(ErrorCode.INCORRECT_USERNAME_OR_PASSWORD, TextConstants.INCORRECT_USERNAME_OR_PASSWORD_MESSAGE);
        }

        return LoginResponse
                .builder()
                .token(JwtUtils.generateToken(request.getUsername()))
                .build();
    }

    private void signUpValidation(SignUpRequest request) {
        if (Objects.isNull(request)) {
            throw new BadRequestException(ErrorCode.REQUIRED_FIELD, String.format(TextConstants.REQUIRED_FIELD_MESSAGE, ApplicationConstants.REQUEST_BODY));
        }

        if (Objects.isNull(request.getUsername())) {
            throw new BadRequestException(ErrorCode.REQUIRED_FIELD, String.format(TextConstants.REQUIRED_FIELD_MESSAGE, ApplicationConstants.REQUEST_BODY));
        }

        passwordValidation(request.getPassword());

        UserDTO userDTO = userService.findByUsername(request.getUsername());
        if (Objects.nonNull(userDTO)) {
            throw new UserAuthenticationException(ErrorCode.USERNAME_ALREADY_REGISTERED, TextConstants.USERNAME_ALREADY_REGISTERED_MESSAGE);
        }
    }

    private void passwordValidation(String password) {
        if (Objects.isNull(password)) {
            throw new BadRequestException(ErrorCode.REQUIRED_FIELD, String.format(TextConstants.REQUIRED_FIELD_MESSAGE, ApplicationConstants.REQUEST_BODY));
        }

        Status passwordAtLeastChracterLongStatus = parameterService.getParamValueAsStatus(ParameterCode.PASSWORD_AT_LEAST_CHARACTER_LONG_CONTROL);
        int atLeastPasswordLength = parameterService.getParamValueAsInteger(ParameterCode.PASSWORD_AT_LEAST_CHARACTER_LONG);

        if (Status.ACTIVE.equals(passwordAtLeastChracterLongStatus) &&
                password.length() < atLeastPasswordLength) {
            throw new BadRequestException(
                    ErrorCode.PASSWORD_AT_LEAST_CHARACTERS_LONG,
                    String.format(TextConstants.PASSWORD_AT_LEAST_CHARACTERS_LONG_MESSAGE, atLeastPasswordLength)
            );
        }

        Status passwordAtLeastOneUppercaseStatus = parameterService.getParamValueAsStatus(ParameterCode.PASSWORD_AT_LEAST_ONE_UPPERCASE_CONTROL);
        if (Status.ACTIVE.equals(passwordAtLeastOneUppercaseStatus) &&
                !password.matches(".*[A-Z].*")) {
            throw new BadRequestException(
                    ErrorCode.PASSWORD_AT_LEAST_ONE_UPPERCASE,
                    TextConstants.PASSWORD_AT_LEAST_ONE_UPPERCASE_MESSAGE
            );
        }

        Status passwordAtLeastOneLowercaseStatus = parameterService.getParamValueAsStatus(ParameterCode.PASSWORD_AT_LEAST_ONE_LOWERCASE_CONTROL);
        if (Status.ACTIVE.equals(passwordAtLeastOneLowercaseStatus) &&
                !password.matches(".*[a-z].*")) {
            throw new BadRequestException(
                    ErrorCode.PASSWORD_AT_LEAST_ONE_LOWERCASE,
                    TextConstants.PASSWORD_AT_LEAST_ONE_LOWERCASE_MESSAGE
            );
        }

        Status passwordAtLeastOneDigitStatus = parameterService.getParamValueAsStatus(ParameterCode.PASSWORD_AT_LEAST_ONE_DIGIT_CONTROL);
        if (Status.ACTIVE.equals(passwordAtLeastOneDigitStatus) &&
                !password.matches(".*\\d.*")) {
            throw new BadRequestException(
                    ErrorCode.PASSWORD_AT_LEAST_ONE_DIGIT,
                    TextConstants.PASSWORD_AT_LEAST_ONE_DIGIT_MESSAGE
            );
        }

        Status passwordAtLeastOneSpecialCharacterStatus = parameterService.getParamValueAsStatus(ParameterCode.PASSWORD_AT_LEAST_ONE_SPECIAL_CHARACTER_CONTROL);
        if (Status.ACTIVE.equals(passwordAtLeastOneSpecialCharacterStatus) &&
                !password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            throw new BadRequestException(
                    ErrorCode.PASSWORD_AT_LEAST_ONE_SPECIAL_CHARACTER,
                    TextConstants.PASSWORD_AT_LEAST_ONE_SPECIAL_CHARACTER_MESSAGE
            );
        }
    }

}
