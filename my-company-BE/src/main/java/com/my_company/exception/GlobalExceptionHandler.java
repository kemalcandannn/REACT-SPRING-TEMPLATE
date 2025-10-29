package com.my_company.exception;

import com.my_company.constants.enums.ErrorCode;
import com.my_company.domain.response.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ServiceResponse<Object>> handleGeneralException(Exception e) {
        log.error("Unexpected error: ", e);

        ServiceResponse<Object> response = ServiceResponse.builder()
                .errorCode(ErrorCode.GENERAL)
                .errorMessage(e.getMessage())
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ServiceResponse<Object>> handleResourceNotFound(ResourceNotFoundException e) {
        logErrorMessage(e);
        ServiceResponse<Object> response = ServiceResponse.builder()
                .errorCode(e.getErrorCode())
                .errorMessage(e.getMessage())
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ServiceResponse<Object>> handleBadRequest(BadRequestException e) {
        logErrorMessage(e);
        ServiceResponse<Object> response = ServiceResponse.builder()
                .errorCode(e.getErrorCode())
                .errorMessage(e.getMessage())
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ServiceResponse<Object>> handleInternalServer(InternalServerException e) {
        logErrorMessage(e);
        ServiceResponse<Object> response = ServiceResponse.builder()
                .errorCode(e.getErrorCode())
                .errorMessage(e.getMessage())
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(UserAuthenticationException.class)
    public ResponseEntity<ServiceResponse<Object>> handleUserValidationException(UserAuthenticationException e) {
        logErrorMessage(e);
        ServiceResponse<Object> response = ServiceResponse.builder()
                .errorCode(e.getErrorCode())
                .errorMessage(e.getMessage())
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    private static void logErrorMessage(Exception e) {
        String errorMessage;

        StackTraceElement[] stack = e.getStackTrace();
        if (stack.length > 0) {
            StackTraceElement origin = stack[0];

            errorMessage = String.format("""
                    %s: {
                        Message: %s,
                        Class: %s,
                        Method: %s,
                        Line: %d
                    }
                    """, e.getClass().getSimpleName(), e.getMessage(), origin.getClassName(), origin.getMethodName(), origin.getLineNumber());
        } else {
            errorMessage = String.format("%s: { Message: %s }", e.getClass().getSimpleName(), e.getMessage());
        }
        log.error(errorMessage);
    }

    // Örnek: validation hataları
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ServiceResponse<Object>> handleValidationError(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");

        ServiceResponse<Object> response = ServiceResponse.builder()
                .errorCode(ErrorCode.VALIDATION)
                .errorMessage(errorMessage)
                .data(null)
                .build();

        return ResponseEntity.badRequest().body(response);
    }
}
