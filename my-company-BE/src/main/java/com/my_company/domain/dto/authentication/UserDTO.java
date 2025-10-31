package com.my_company.domain.dto.authentication;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.my_company.constants.enums.AuthProvider;
import com.my_company.constants.enums.Language;
import com.my_company.constants.enums.Status;
import com.my_company.domain.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends BaseDto<String> {
    private String username;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String password2;
    @JsonIgnore
    private String password3;
    private LocalDateTime passwordValidUntil;
    private AuthProvider provider;
    private String providerId;
    private Status status;
    private Language language;
    private LocalDateTime createdAt;
}
