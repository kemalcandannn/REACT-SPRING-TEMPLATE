package com.my_company.base.domain.dto.authentication;

import com.my_company.base.domain.dto.BaseDto;
import com.my_company.base.domain.entity.authentication.RoleMenuId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RoleMenuDTO extends BaseDto<RoleMenuId> {

    private String roleCode;
    private String menuCode;
    private LocalDateTime createdAt;

}
