package com.my_company.domain.dto.authentication;

import com.my_company.constants.enums.TokenStatus;
import com.my_company.constants.enums.TokenType;
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
public class UserTokenDTO extends BaseDto<Long> {
    private String username;
    private TokenType type;
    private TokenStatus status;
    private String token;
    private LocalDateTime expiresAt;
    private LocalDateTime usedAt;
    private LocalDateTime createdAt;
}
