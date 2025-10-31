package com.my_company.controller.authentication;

import com.my_company.constants.PathConstants;
import com.my_company.constants.TextConstants;
import com.my_company.domain.request.authentication.*;
import com.my_company.domain.response.ServiceResponse;
import com.my_company.domain.response.authentication.LoginResponse;
import com.my_company.domain.response.authentication.UserResponse;
import com.my_company.service.authentication.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(PathConstants.API_V1_AUTHENTICATION_URL)
@Slf4j
@Tag(name = TextConstants.AUTHENTICATION, description = "Authorization Services for Users")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping(PathConstants.REGISTER_URL)
    @Operation(summary = "Register", description = "Register Operation")
    public ResponseEntity<ServiceResponse<LoginResponse>> register(@Valid @RequestBody RegisterRequest request) {
        LoginResponse loginResponse = authenticationService.register(request);
        return ResponseEntity.ok(
                ServiceResponse
                        .<LoginResponse>builder()
                        .data(loginResponse)
                        .build());
    }

    @PostMapping(PathConstants.VERIFY_ACCOUNT_URL)
    public ResponseEntity<Void> verifyAccount(@Valid @RequestBody VerifyAccountRequest request) {
        authenticationService.verifyAccount(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping(PathConstants.LOGIN_URL)
    @Operation(summary = "Login", description = "Login Operation")
    public ResponseEntity<ServiceResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse loginResponse = authenticationService.login(request);
        return ResponseEntity.ok(
                ServiceResponse
                        .<LoginResponse>builder()
                        .data(loginResponse)
                        .build());
    }

    @PostMapping(PathConstants.GOOGLE_LOGIN_URL)
    public ResponseEntity<ServiceResponse<LoginResponse>> googleLogin(@Valid @RequestBody GoogleLoginRequest request) {
        LoginResponse loginResponse = authenticationService.googleLogin(request);
        return ResponseEntity.ok(
                ServiceResponse
                        .<LoginResponse>builder()
                        .data(loginResponse)
                        .build());
    }

    @PostMapping(PathConstants.RESET_PASSWORD_URL)
    @Operation(summary = "Reset Password", description = "Reset Password Operation")
    public ResponseEntity<ServiceResponse<Void>> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        authenticationService.resetPassword(request);
        return ResponseEntity.ok(
                ServiceResponse
                        .<Void>builder()
                        .data(null)
                        .build());
    }

    @PostMapping(PathConstants.SEND_PASSWORD_RESET_LINK_URL)
    @Operation(summary = "Send Password Reset Link", description = "Send Password Reset Link Operation")
    public ResponseEntity<ServiceResponse<Void>> sendPasswordResetLink(@Valid @RequestBody SendPasswordResetLinkRequest request) {
        authenticationService.sendPasswordResetLink(request);
        return ResponseEntity.ok(
                ServiceResponse
                        .<Void>builder()
                        .data(null)
                        .build());
    }

    @PostMapping("/change-password")
    @Operation(summary = "Change Password", description = "Change Password Operation")
    public ResponseEntity<ServiceResponse<Void>> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        authenticationService.changePassword(request);
        return ResponseEntity.ok(
                ServiceResponse
                        .<Void>builder()
                        .data(null)
                        .build());
    }

    @GetMapping("/user")
    @Operation(summary = "User Details", description = "Find User Details From JWT Token")
    public ResponseEntity<ServiceResponse<UserResponse>> extractAuthenticationFromToken() {
        UserResponse userResponse = authenticationService.extractAuthenticationFromToken();
        return ResponseEntity.ok(
                ServiceResponse
                        .<UserResponse>builder()
                        .data(userResponse)
                        .build());
    }
}
