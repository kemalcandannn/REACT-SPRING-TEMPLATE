package com.my_company.domain.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class RoleMenuDTO {
    private String roleCode;
    private String menuCode;
    private LocalDateTime createdAt;
}
