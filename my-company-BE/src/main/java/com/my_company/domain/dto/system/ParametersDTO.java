package com.my_company.domain.dto.system;

import com.my_company.constants.enums.ParametersType;
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
public class ParametersDTO extends BaseDto<String> {
    private String code;
    private ParametersType type;
    private String value;
    private LocalDateTime createdAt;
}
