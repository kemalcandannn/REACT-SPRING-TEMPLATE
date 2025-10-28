package com.my_company.exception;

import com.my_company.constants.enums.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InternalServerException extends RuntimeException {
    private final ErrorCode errorCode;

    public InternalServerException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
