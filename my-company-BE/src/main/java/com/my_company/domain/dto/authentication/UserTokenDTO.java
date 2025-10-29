package com.my_company.domain.dto.authentication;

import com.my_company.constants.enums.TokenStatus;
import com.my_company.constants.enums.TokenType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenDTO {
    private Long id;
    private String username;
    private TokenType type;
    private TokenStatus status;
    private String token;
    private LocalDateTime expiresAt;
    private LocalDateTime usedAt;
    private String ipAddress;
    private LocalDateTime createdAt;
}
