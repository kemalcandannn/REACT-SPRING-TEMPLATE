package com.my_company.base.domain.dto.system;

import com.my_company.base.constants.enums.ParameterType;
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
public class ParameterDTO extends BaseDto<String> {

    private String code;
    private ParameterType type;
    private String value;
    private LocalDateTime createdAt;

}
