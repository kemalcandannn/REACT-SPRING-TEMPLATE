package com.my_company.base.domain.dto.authentication;

import com.my_company.base.domain.dto.BaseDto;
import com.my_company.base.domain.entity.authentication.UserRoleId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserRoleDTO extends BaseDto<UserRoleId> {

    private String username;
    private String roleCode;
    private LocalDateTime createdAt;

}
