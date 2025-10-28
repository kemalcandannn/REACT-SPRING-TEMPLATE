package com.my_company.domain.response;

import com.my_company.constants.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceResponse<T> {
    private boolean success;
    private int statusCode;
    private ErrorCode errorCode;
    private String errorMessage;
    private T data;
    private LocalDateTime timestamp;
}
