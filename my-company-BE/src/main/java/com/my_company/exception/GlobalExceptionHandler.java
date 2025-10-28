package com.my_company.exception;

import com.my_company.domain.response.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ServiceResponse<Object>> handleGeneralException(Exception e) {
        log.error("Unexpected error: ", e);

        ServiceResponse<Object> response = ServiceResponse.builder()
                .success(false)
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errorMessage(e.getMessage())
                .data(null)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ServiceResponse<Object>> handleResourceNotFound(ResourceNotFoundException e) {
        String errorMessage = getErrorMessage(e);
        log.error(errorMessage);

        ServiceResponse<Object> response = ServiceResponse.builder()
                .success(false)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .errorCode(e.getErrorCode())
                .errorMessage(e.getMessage())
                .data(null)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ServiceResponse<Object>> handleBadRequest(BadRequestException e) {
        String errorMessage = getErrorMessage(e);

        log.error(errorMessage);
        ServiceResponse<Object> response = ServiceResponse.builder()
                .success(false)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .errorCode(e.getErrorCode())
                .errorMessage(errorMessage)
                .data(null)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(UserAuthenticationException.class)
    public ResponseEntity<ServiceResponse<Object>> handleUserValidationException(UserAuthenticationException e) {
        String errorMessage = getErrorMessage(e);
        log.error(errorMessage);

        ServiceResponse<Object> response = ServiceResponse.builder()
                .success(false)
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errorCode(e.getErrorCode())
                .errorMessage(e.getMessage())
                .data(null)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    private static String getErrorMessage(Exception e) {
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
        return errorMessage;
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
                .success(false)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .errorMessage(errorMessage)
                .data(null)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

}