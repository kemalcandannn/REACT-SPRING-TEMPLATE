package com.my_company.domain.response;

import com.my_company.constants.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceResponse<T> {
    private ErrorCode errorCode;
    private String errorMessage;
    private T data;
}
