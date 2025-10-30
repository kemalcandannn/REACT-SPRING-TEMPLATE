package com.my_company.domain.request.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@Builder
public class AuthenticationRequest {
    private String token;
    private String username;
    private String oldPassword;
    private String password;
    private String confirmPassword;
}
