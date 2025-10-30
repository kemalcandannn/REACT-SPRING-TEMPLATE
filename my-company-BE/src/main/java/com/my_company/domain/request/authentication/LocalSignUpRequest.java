package com.my_company.domain.request.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class LocalSignUpRequest {
    private String username;
    private String password;
    private String confirmPassword;
}
