package com.my_company.service.authentication;

import com.my_company.cache.ParametersCache;
import com.my_company.constants.ApplicationConstants;
import com.my_company.constants.TextConstants;
import com.my_company.constants.enums.ErrorCode;
import com.my_company.constants.enums.ParametersCode;
import com.my_company.domain.dto.authentication.UserDTO;
import com.my_company.domain.entity.authentication.User;
import com.my_company.domain.request.authentication.ChangePasswordRequest;
import com.my_company.domain.request.authentication.ForgotPasswordRequest;
import com.my_company.domain.request.authentication.LoginRequest;
import com.my_company.domain.request.authentication.SignUpRequest;
import com.my_company.domain.response.authentication.LoginResponse;
import com.my_company.exception.BadRequestException;
import com.my_company.exception.InternalServerException;
import com.my_company.exception.UserAuthenticationException;
import com.my_company.mapper.authentication.UserMapper;
import com.my_company.utils.JwtUtils;
import com.my_company.utils.PasswordUtils;
import com.my_company.utils.SecurityUtils;
import com.my_company.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse localSignUp(SignUpRequest request) {
        if (Objects.isNull(request)) {
            throw new BadRequestException(ErrorCode.REQUIRED_FIELD, String.format(TextConstants.REQUIRED_FIELD_MESSAGE, ApplicationConstants.REQUEST_BODY));
        }

        if (StringUtils.isNullOrBlank(request.getUsername())) {
            throw new BadRequestException(ErrorCode.REQUIRED_FIELD, String.format(TextConstants.REQUIRED_FIELD_MESSAGE, ApplicationConstants.REQUEST_BODY));
        }

        PasswordUtils.passwordValidation(passwordEncoder, null, request.getPassword());

        UserDTO userDTO = userService.findById(request.getUsername(), false);
        if (Objects.nonNull(userDTO)) {
            throw new UserAuthenticationException(ErrorCode.USERNAME_ALREADY_REGISTERED, TextConstants.USERNAME_ALREADY_REGISTERED_MESSAGE);
        }

        int passwordExpirationDays = ParametersCache.getParamValueAsInteger(ParametersCode.PASSWORD_EXPIRATION_DAYS);
        userDTO = userMapper.signUpRequestToDTO(request, passwordEncoder, passwordExpirationDays);
        userDTO = userService.saveOrUpdate(userDTO);

        return login(
                LoginRequest
                        .builder()
                        .username(userDTO.getUsername())
                        .password(request.getPassword())
                        .build()
        );
    }

    public LoginResponse login(LoginRequest request) {
        if (Objects.isNull(request)) {
            throw new BadRequestException(ErrorCode.REQUIRED_FIELD, String.format(TextConstants.REQUIRED_FIELD_MESSAGE, ApplicationConstants.REQUEST_BODY));
        }

        User user = userService.findAuthenticationUserByUsername(request.getUsername());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserAuthenticationException(ErrorCode.INCORRECT_USERNAME_OR_PASSWORD, TextConstants.INCORRECT_USERNAME_OR_PASSWORD_MESSAGE);
        }

        return LoginResponse
                .builder()
                .token(JwtUtils.generateToken(request.getUsername()))
                .build();
    }

    public void forgotPassword(ForgotPasswordRequest request) {
        log.info("{}", request);
    }

    public void changePassword(ChangePasswordRequest request) {
        if (Objects.isNull(request)) {
            throw new BadRequestException(ErrorCode.REQUIRED_FIELD, String.format(TextConstants.REQUIRED_FIELD_MESSAGE, ApplicationConstants.REQUEST_BODY));
        }

        if (StringUtils.isNullOrBlank(request.getOldPassword())) {
            throw new BadRequestException(ErrorCode.REQUIRED_FIELD, String.format(TextConstants.REQUIRED_FIELD_MESSAGE, ChangePasswordRequest.Fields.oldPassword));
        }

        if (StringUtils.isNullOrBlank(request.getNewPassword())) {
            throw new BadRequestException(ErrorCode.REQUIRED_FIELD, String.format(TextConstants.REQUIRED_FIELD_MESSAGE, ChangePasswordRequest.Fields.newPassword));
        }

        if (StringUtils.isNullOrBlank(request.getConfirmPassword())) {
            throw new BadRequestException(ErrorCode.REQUIRED_FIELD, String.format(TextConstants.REQUIRED_FIELD_MESSAGE, ChangePasswordRequest.Fields.confirmPassword));
        }

        User user = SecurityUtils.getCurrentUser();
        assert user != null;

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new UserAuthenticationException(ErrorCode.INCORRECT_OLD_PASSWORD, TextConstants.INCORRECT_OLD_PASSWORD_MESSAGE);
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new InternalServerException(ErrorCode.NEW_PASSWORD_DOES_NOT_CONFIRM, TextConstants.NEW_PASSWORD_DOES_NOT_CONFIRM_MESSAGE);
        }

        PasswordUtils.passwordValidation(passwordEncoder, user, request.getNewPassword());

        userService.changePassword(user, request);
    }
}
