package com.my_company.service.authentication;

import com.my_company.cache.ParameterCache;
import com.my_company.constants.ApplicationConstants;
import com.my_company.constants.TextConstants;
import com.my_company.constants.enums.ErrorCode;
import com.my_company.constants.enums.ParameterCode;
import com.my_company.constants.enums.Status;
import com.my_company.domain.dto.authentication.RoleMenuDTO;
import com.my_company.domain.dto.authentication.UserDTO;
import com.my_company.domain.dto.authentication.UserMenuDTO;
import com.my_company.domain.dto.authentication.UserTokenDTO;
import com.my_company.domain.entity.authentication.User;
import com.my_company.domain.request.authentication.ChangePasswordRequest;
import com.my_company.domain.request.authentication.LoginRequest;
import com.my_company.domain.request.authentication.SendPasswordResetLinkRequest;
import com.my_company.domain.request.authentication.SignUpRequest;
import com.my_company.domain.response.authentication.LoginResponse;
import com.my_company.domain.response.authentication.UserResponse;
import com.my_company.exception.BadRequestException;
import com.my_company.exception.InternalServerException;
import com.my_company.exception.UserAuthenticationException;
import com.my_company.mapper.authentication.UserMapper;
import com.my_company.mapper.authentication.UserTokenMapper;
import com.my_company.service.email.EmailService;
import com.my_company.utils.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final UserMapper userMapper;
    private final UserTokenService userTokenService;
    private final UserTokenMapper userTokenMapper;
    private final EmailService emailService;
    private final UserMenuService userMenuService;
    private final RoleMenuService roleMenuService;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse localSignUp(SignUpRequest request) {
        if (Objects.isNull(request)) {
            throw new BadRequestException(ErrorCode.REQUIRED_FIELD, String.format(TextConstants.REQUIRED_FIELD_MESSAGE, ApplicationConstants.REQUEST_BODY));
        }

        if (StringUtils.isNullOrBlank(request.getUsername())) {
            throw new BadRequestException(ErrorCode.REQUIRED_FIELD, String.format(TextConstants.REQUIRED_FIELD_MESSAGE, SignUpRequest.Fields.username));
        }

        if (StringUtils.isNullOrBlank(request.getPassword())) {
            throw new BadRequestException(ErrorCode.REQUIRED_FIELD, String.format(TextConstants.REQUIRED_FIELD_MESSAGE, SignUpRequest.Fields.password));
        }

        PasswordUtils.passwordValidation(passwordEncoder, null, request.getPassword());

        UserDTO userDTO = userService.findById(request.getUsername(), false);
        if (Objects.nonNull(userDTO)) {
            throw new UserAuthenticationException(ErrorCode.USERNAME_ALREADY_REGISTERED, TextConstants.USERNAME_ALREADY_REGISTERED_MESSAGE);
        }

        Integer passwordExpirationDays = Status.ACTIVE.equals(ParameterCache.getParamValueAsStatus(ParameterCode.PASSWORD_EXPIRATION_CONTROL)) ?
                ParameterCache.getParamValueAsInteger(ParameterCode.PASSWORD_EXPIRATION_DAYS)
                : null;

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

        if (StringUtils.isNullOrBlank(request.getUsername())) {
            throw new BadRequestException(ErrorCode.REQUIRED_FIELD, String.format(TextConstants.REQUIRED_FIELD_MESSAGE, LoginRequest.Fields.username));
        }

        if (StringUtils.isNullOrBlank(request.getPassword())) {
            throw new BadRequestException(ErrorCode.REQUIRED_FIELD, String.format(TextConstants.REQUIRED_FIELD_MESSAGE, LoginRequest.Fields.password));
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

    @Transactional
    public void sendPasswordResetLink(SendPasswordResetLinkRequest request) {
        if (Objects.isNull(request)) {
            throw new BadRequestException(ErrorCode.REQUIRED_FIELD, String.format(TextConstants.REQUIRED_FIELD_MESSAGE, ApplicationConstants.REQUEST_BODY));
        }

        if (StringUtils.isNullOrBlank(request.getUsername())) {
            throw new BadRequestException(ErrorCode.REQUIRED_FIELD, String.format(TextConstants.REQUIRED_FIELD_MESSAGE, SendPasswordResetLinkRequest.Fields.username));
        }

        UserDTO userDTO = userService.findById(request.getUsername(), true);
        String token = UUID.randomUUID().toString();
        while (userTokenService.findByToken(token) != null) {
            token = UUID.randomUUID().toString();
        }

        Integer tokenExpirationMinutes = Status.ACTIVE.equals(ParameterCache.getParamValueAsStatus(ParameterCode.TOKEN_EXPIRATION_CONTROL)) ?
                ParameterCache.getParamValueAsInteger(ParameterCode.TOKEN_EXPIRATION_MINUTES)
                : null;

        UserTokenDTO userTokenDTO = userTokenMapper.extraxtUserTokenDTO(userDTO, token, tokenExpirationMinutes);
        userTokenService.saveOrUpdate(userTokenDTO);
        emailService.sendPasswordResetMail(userDTO.getUsername(), token);
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

        User user = userService.findAuthenticationUserByUsername(SecurityUtils.getCurrentUsername());

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new UserAuthenticationException(ErrorCode.INCORRECT_OLD_PASSWORD, TextConstants.INCORRECT_OLD_PASSWORD_MESSAGE);
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new InternalServerException(ErrorCode.NEW_PASSWORD_DOES_NOT_CONFIRM, TextConstants.NEW_PASSWORD_DOES_NOT_CONFIRM_MESSAGE);
        }

        PasswordUtils.passwordValidation(passwordEncoder, user, request.getNewPassword());

        userService.changePassword(user, request);
    }

    public UserResponse extractAuthenticationFromToken() {
        User user = userService.findAuthenticationUserByUsername(SecurityUtils.getCurrentUsername());

        List<UserMenuDTO> userMenuDTOList = userMenuService.findByUsername(user.getUsername());

        Set<String> menuSet = new HashSet<>(userMenuDTOList
                .stream()
                .map(UserMenuDTO::getMenuCode)
                .toList());

        if (CollectionUtils.isNotEmpty(SecurityUtils.getAuthorities())) {
            List<RoleMenuDTO> roleMenuDTOList = roleMenuService.findByRoleCodeIn(SecurityUtils.getAuthorities()
                    .stream()
                    .map(SimpleGrantedAuthority::getAuthority)
                    .toList());

            menuSet.addAll(roleMenuDTOList
                    .stream()
                    .map(RoleMenuDTO::getMenuCode)
                    .toList());
        }

        return userMapper.entityToResponse(user, SecurityUtils.getAuthorities(), new ArrayList<>(menuSet));
    }
}
