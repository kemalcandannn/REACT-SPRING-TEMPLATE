package com.my_company.domain.dto.authentication;

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
public class RoleDTO extends BaseDto<String> {

    private String code;
    private String name;
    private LocalDateTime createdAt;

}
