package com.my_company.controller.authentication;

import com.my_company.constants.PathConstants;
import com.my_company.constants.TextConstants;
import com.my_company.domain.request.authentication.ForgotPasswordRequest;
import com.my_company.domain.request.authentication.LoginRequest;
import com.my_company.domain.request.authentication.SignUpRequest;
import com.my_company.domain.response.ServiceResponse;
import com.my_company.domain.response.authentication.LoginResponse;
import com.my_company.service.authentication.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping(PathConstants.API_V1_AUTHENTICATION_URL)
@Slf4j
@Tag(name = TextConstants.AUTHENTICATION, description = "Authorization Services for Users")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping(PathConstants.LOCAL_SIGN_UP_URL)
    @Operation(summary = "Sign Up", description = "Local Sign Up Operation")
    public ResponseEntity<ServiceResponse<LoginResponse>> localSignUp(@Valid @RequestBody SignUpRequest request) {
        LoginResponse loginResponse = authenticationService.localSignUp(request);
        return ResponseEntity.ok(
                ServiceResponse
                        .<LoginResponse>builder()
                        .success(true)
                        .statusCode(HttpStatus.OK.value())
                        .data(loginResponse)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @PostMapping(PathConstants.LOGIN_URL)
    @Operation(summary = "Login", description = "Login Operation")
    public ResponseEntity<ServiceResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse loginResponse = authenticationService.login(request);
        return ResponseEntity.ok(
                ServiceResponse
                        .<LoginResponse>builder()
                        .success(true)
                        .statusCode(HttpStatus.OK.value())
                        .data(loginResponse)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @PostMapping(PathConstants.FORGOT_PASSWORD_URL)
    @Operation(summary = "Forgot Password", description = "Forgot Password Operation")
    public ResponseEntity<ServiceResponse<Void>> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        authenticationService.forgotPassword(request);
        return ResponseEntity.ok(
                ServiceResponse
                        .<Void>builder()
                        .success(true)
                        .statusCode(HttpStatus.OK.value())
                        .data(null)
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}
