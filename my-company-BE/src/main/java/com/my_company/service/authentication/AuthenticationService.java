package com.my_company.service.authentication;

import com.my_company.cache.ParameterCache;
import com.my_company.constants.ApplicationConstants;
import com.my_company.constants.TextConstants;
import com.my_company.constants.enums.ErrorCode;
import com.my_company.constants.enums.ParameterCode;
import com.my_company.constants.enums.Status;
import com.my_company.constants.enums.TokenStatus;
import com.my_company.domain.dto.authentication.RoleMenuDTO;
import com.my_company.domain.dto.authentication.UserDTO;
import com.my_company.domain.dto.authentication.UserMenuDTO;
import com.my_company.domain.dto.authentication.UserTokenDTO;
import com.my_company.domain.entity.authentication.User;
import com.my_company.domain.request.authentication.*;
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

import java.time.LocalDateTime;
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

    public LoginResponse localSignUp(LocalSignUpRequest request) {
        if (Objects.isNull(request)) {
            throw new BadRequestException(ErrorCode.REQUIRED_FIELD, String.format(TextConstants.REQUIRED_FIELD_MESSAGE, ApplicationConstants.REQUEST_BODY));
        }

        if (StringUtils.isNullOrBlank(request.getUsername())) {
            throw new BadRequestException(ErrorCode.REQUIRED_FIELD, String.format(TextConstants.REQUIRED_FIELD_MESSAGE, LocalSignUpRequest.Fields.username));
        }

        if (StringUtils.isNullOrBlank(request.getPassword())) {
            throw new BadRequestException(ErrorCode.REQUIRED_FIELD, String.format(TextConstants.REQUIRED_FIELD_MESSAGE, LocalSignUpRequest.Fields.password));
        }

        if (StringUtils.isNullOrBlank(request.getConfirmPassword())) {
            throw new BadRequestException(ErrorCode.REQUIRED_FIELD, String.format(TextConstants.REQUIRED_FIELD_MESSAGE, LocalSignUpRequest.Fields.confirmPassword));
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new InternalServerException(ErrorCode.PASSWORD_DOES_NOT_CONFIRM, "Password does not confirm.");
        }

        PasswordUtils.passwordValidation(passwordEncoder, null, request.getPassword());

        UserDTO userDTO = userService.findById(request.getUsername(), false);
        if (Objects.nonNull(userDTO)) {
            throw new UserAuthenticationException(ErrorCode.USERNAME_ALREADY_REGISTERED, "The username is already registered in the system.");
        }

        Integer passwordExpirationDays = ParameterCache.getParamValueAsIntegerWithControl(ParameterCode.PASSWORD_EXPIRATION_CONTROL, ParameterCode.PASSWORD_EXPIRATION_DAYS);

        userDTO = userMapper.localSignUpRequestToDTO(request, passwordEncoder, passwordExpirationDays);
        userDTO = userService.saveOrUpdate(userDTO);

        String token = userTokenService.getRandomToken();
        Integer tokenExpirationMinutes = ParameterCache.getParamValueAsIntegerWithControl(ParameterCode.VERIFY_ACCOUNT_TOKEN_EXPIRATION_CONTROL, ParameterCode.VERIFY_ACCOUNT_TOKEN_EXPIRATION_MINUTES);

        UserTokenDTO userTokenDTO = userTokenMapper.extraxtAccountVerificationUserTokenDTO(userDTO, token, tokenExpirationMinutes);
        userTokenService.saveOrUpdate(userTokenDTO);
        emailService.sendAccountVerificationMail(userDTO.getUsername(), userTokenDTO.getToken());

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
        String token = userTokenService.getRandomToken();
        Integer tokenExpirationMinutes = ParameterCache.getParamValueAsIntegerWithControl(ParameterCode.RESET_PASSWORD_TOKEN_EXPIRATION_CONTROL, ParameterCode.RESET_PASSWORD_TOKEN_EXPIRATION_MINUTES);

        UserTokenDTO userTokenDTO = userTokenMapper.extraxtPasswordResetUserTokenDTO(userDTO, token, tokenExpirationMinutes);
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

        if (StringUtils.isNullOrBlank(request.getPassword())) {
            throw new BadRequestException(ErrorCode.REQUIRED_FIELD, String.format(TextConstants.REQUIRED_FIELD_MESSAGE, ChangePasswordRequest.Fields.password));
        }

        if (StringUtils.isNullOrBlank(request.getConfirmPassword())) {
            throw new BadRequestException(ErrorCode.REQUIRED_FIELD, String.format(TextConstants.REQUIRED_FIELD_MESSAGE, ChangePasswordRequest.Fields.confirmPassword));
        }

        User user = userService.findAuthenticationUserByUsername(SecurityUtils.getCurrentUsername());

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new UserAuthenticationException(ErrorCode.INCORRECT_OLD_PASSWORD, "Incorrect old password.");
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new InternalServerException(ErrorCode.PASSWORD_DOES_NOT_CONFIRM, "Password does not confirm.");
        }

        PasswordUtils.passwordValidation(passwordEncoder, user, request.getPassword());

        userService.changePassword(user, request.getPassword());
    }

    public UserResponse extractAuthenticationFromToken() {
        User user = userService.findAuthenticationUserByUsername(SecurityUtils.getCurrentUsername());

        List<UserMenuDTO> userMenuDTOList = userMenuService.findByUsername(user.getUsername());

        List<String> roleList = new ArrayList<>();
        Set<String> menuSet = new HashSet<>(userMenuDTOList
                .stream()
                .map(UserMenuDTO::getMenuCode)
                .toList());

        if (CollectionUtils.isNotEmpty(SecurityUtils.getAuthorities())) {
            roleList = SecurityUtils.getAuthorities()
                    .stream()
                    .map(SimpleGrantedAuthority::getAuthority)
                    .toList();

            List<RoleMenuDTO> roleMenuDTOList = roleMenuService.findByRoleCodeIn(SecurityUtils.getAuthorities()
                    .stream()
                    .map(SimpleGrantedAuthority::getAuthority)
                    .toList());

            menuSet.addAll(roleMenuDTOList
                    .stream()
                    .map(RoleMenuDTO::getMenuCode)
                    .toList());
        }

        return userMapper.entityToResponse(user, roleList, new ArrayList<>(menuSet));
    }

    public void resetPassword(ResetPasswordRequest request) {
        if (Objects.isNull(request)) {
            throw new BadRequestException(ErrorCode.REQUIRED_FIELD, String.format(TextConstants.REQUIRED_FIELD_MESSAGE, ApplicationConstants.REQUEST_BODY));
        }

        if (StringUtils.isNullOrBlank(request.getToken())) {
            throw new BadRequestException(ErrorCode.REQUIRED_FIELD, String.format(TextConstants.REQUIRED_FIELD_MESSAGE, ResetPasswordRequest.Fields.token));
        }

        if (StringUtils.isNullOrBlank(request.getPassword())) {
            throw new BadRequestException(ErrorCode.REQUIRED_FIELD, String.format(TextConstants.REQUIRED_FIELD_MESSAGE, ResetPasswordRequest.Fields.password));
        }

        if (StringUtils.isNullOrBlank(request.getConfirmPassword())) {
            throw new BadRequestException(ErrorCode.REQUIRED_FIELD, String.format(TextConstants.REQUIRED_FIELD_MESSAGE, ResetPasswordRequest.Fields.confirmPassword));
        }

        UserTokenDTO userTokenDTO = userTokenService.findByTokenAndStatus(request.getToken(), TokenStatus.ACTIVE);
        if (userTokenDTO == null) {
            throw new BadRequestException(ErrorCode.RESET_PASSWORD_TOKEN_NOT_FOUND, "Record not found from token in the request");
        }

        User user = userService.findAuthenticationUserByUsername(userTokenDTO.getUsername());

        if (userTokenDTO.getExpiresAt() != null && userTokenDTO.getExpiresAt().isBefore(LocalDateTime.now())) {
            userTokenDTO.setStatus(TokenStatus.EXPIRED);
            userTokenDTO = userTokenService.saveOrUpdate(userTokenDTO);
            userTokenDTO.setId(null);
            userTokenDTO.setStatus(TokenStatus.ACTIVE);

            String token = userTokenService.getRandomToken();
            userTokenDTO.setToken(token);
            Integer tokenExpirationMinutes = ParameterCache.getParamValueAsIntegerWithControl(ParameterCode.RESET_PASSWORD_TOKEN_EXPIRATION_CONTROL, ParameterCode.RESET_PASSWORD_TOKEN_EXPIRATION_MINUTES);

            userTokenDTO.setExpiresAt(userTokenMapper.getExpiresAt(tokenExpirationMinutes));
            userTokenService.saveOrUpdate(userTokenDTO);
            emailService.sendPasswordResetMail(user.getUsername(), userTokenDTO.getToken());
            throw new InternalServerException(ErrorCode.RESET_PASSWORD_TOKEN_EXPIRED, "Reset Password Token has expired.");
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new InternalServerException(ErrorCode.PASSWORD_DOES_NOT_CONFIRM, "Password does not confirm.");
        }

        PasswordUtils.passwordValidation(passwordEncoder, user, request.getPassword());
        userService.changePassword(user, request.getPassword());
        userTokenDTO.setUsedAt(LocalDateTime.now());
        userTokenDTO.setStatus(TokenStatus.USED);
        userTokenService.saveOrUpdate(userTokenDTO);
    }

    public void verifyAccount(VerifyAccountRequest request) {
        if (Objects.isNull(request)) {
            throw new BadRequestException(ErrorCode.REQUIRED_FIELD, String.format(TextConstants.REQUIRED_FIELD_MESSAGE, ApplicationConstants.REQUEST_BODY));
        }

        if (StringUtils.isNullOrBlank(request.getToken())) {
            throw new BadRequestException(ErrorCode.REQUIRED_FIELD, String.format(TextConstants.REQUIRED_FIELD_MESSAGE, VerifyAccountRequest.Fields.token));
        }

        UserTokenDTO userTokenDTO = userTokenService.findByTokenAndStatus(request.getToken(), TokenStatus.ACTIVE);
        if (userTokenDTO == null) {
            throw new BadRequestException(ErrorCode.RESET_PASSWORD_TOKEN_NOT_FOUND, "Record not found from token in the request");
        }

        User user = userService.findAuthenticationUserByUsername(userTokenDTO.getUsername());

        if (userTokenDTO.getExpiresAt() != null && userTokenDTO.getExpiresAt().isBefore(LocalDateTime.now())) {
            userTokenDTO.setStatus(TokenStatus.EXPIRED);
            userTokenDTO = userTokenService.saveOrUpdate(userTokenDTO);
            userTokenDTO.setId(null);
            userTokenDTO.setStatus(TokenStatus.ACTIVE);

            String token = userTokenService.getRandomToken();
            userTokenDTO.setToken(token);
            Integer tokenExpirationMinutes = ParameterCache.getParamValueAsIntegerWithControl(ParameterCode.VERIFY_ACCOUNT_TOKEN_EXPIRATION_CONTROL, ParameterCode.VERIFY_ACCOUNT_TOKEN_EXPIRATION_MINUTES);

            userTokenDTO.setExpiresAt(userTokenMapper.getExpiresAt(tokenExpirationMinutes));
            userTokenService.saveOrUpdate(userTokenDTO);
            emailService.sendAccountVerificationMail(user.getUsername(), userTokenDTO.getToken());
            throw new InternalServerException(ErrorCode.RESET_PASSWORD_TOKEN_EXPIRED, "Reset Password Token has expired.");
        }

        UserDTO userDTO = userMapper.entityToDto(user);
        userDTO.setStatus(Status.ACTIVE);
        userService.saveOrUpdate(userDTO);
        userTokenDTO.setUsedAt(LocalDateTime.now());
        userTokenDTO.setStatus(TokenStatus.USED);
        userTokenService.saveOrUpdate(userTokenDTO);
    }
}
