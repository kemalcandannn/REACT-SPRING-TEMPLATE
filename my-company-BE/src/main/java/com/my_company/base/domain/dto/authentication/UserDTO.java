package com.my_company.base.domain.dto.authentication;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.my_company.base.constants.enums.AuthProvider;
import com.my_company.base.domain.dto.BaseDto;
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
    private AuthProvider provider;
    private String providerId;
    private LocalDateTime createdAt;

}
