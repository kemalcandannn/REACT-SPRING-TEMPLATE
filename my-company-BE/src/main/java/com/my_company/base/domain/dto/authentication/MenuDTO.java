package com.my_company.base.domain.dto.authentication;

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
public class MenuDTO extends BaseDto<String> {

    private String code;
    private String name;
    private String path;
    private LocalDateTime createdAt;

}
