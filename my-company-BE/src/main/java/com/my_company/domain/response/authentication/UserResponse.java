package com.my_company.domain.response.authentication;

import com.my_company.constants.enums.AuthProvider;
import com.my_company.constants.enums.Language;
import com.my_company.constants.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String username;
    private LocalDateTime passwordValidUntil;
    private AuthProvider provider;
    private String providerId;
    private Status status;
    private Language language;
    private LocalDateTime createdAt;
    private List<String> roleList;
    private List<String> menuList;
}
