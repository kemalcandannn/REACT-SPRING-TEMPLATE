package com.my_company.utils;

import com.my_company.cache.ParameterCache;
import com.my_company.constants.ApplicationConstants;
import com.my_company.constants.TextConstants;
import com.my_company.constants.enums.ErrorCode;
import com.my_company.constants.enums.ParameterCode;
import com.my_company.constants.enums.Status;
import com.my_company.domain.entity.authentication.User;
import com.my_company.exception.BadRequestException;
import com.my_company.exception.InternalServerException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;
import java.util.stream.Stream;

public class PasswordUtils {
    private PasswordUtils() {
        throw new UnsupportedOperationException(ApplicationConstants.UTILITY_CLASS);
    }

    public static void passwordValidation(PasswordEncoder passwordEncoder, User user, String password) {
        if (StringUtils.isNullOrBlank(password)) {
            throwRequiredFieldException();
        }

        validateLength(password);
        validateUppercase(password);
        validateLowercase(password);
        validateDigit(password);
        validateSpecialCharacter(password);
        validatePreviousPasswords(passwordEncoder, user, password);
    }

    private static void throwRequiredFieldException() {
        throw new BadRequestException(
                ErrorCode.REQUIRED_FIELD,
                String.format(TextConstants.REQUIRED_FIELD_MESSAGE, ApplicationConstants.REQUEST_BODY)
        );
    }

    private static void validateLength(String password) {
        if (isActive(ParameterCode.PASSWORD_AT_LEAST_CHARACTER_LONG_CONTROL)) {
            int minLength = ParameterCache.getParamValueAsInteger(ParameterCode.PASSWORD_AT_LEAST_CHARACTER_LONG);
            if (password.length() < minLength) {
                throw new InternalServerException(
                        ErrorCode.PASSWORD_AT_LEAST_CHARACTERS_LONG,
                        String.format("Password must be at least [%d] characters long.", minLength)
                );
            }
        }
    }

    private static void validateUppercase(String password) {
        validatePattern(password,
                ParameterCode.PASSWORD_AT_LEAST_ONE_UPPERCASE_CONTROL,
                ".*[A-Z].*",
                ErrorCode.PASSWORD_AT_LEAST_ONE_UPPERCASE,
                "Password must contain at least one uppercase letter.");
    }

    private static void validateLowercase(String password) {
        validatePattern(password,
                ParameterCode.PASSWORD_AT_LEAST_ONE_LOWERCASE_CONTROL,
                ".*[a-z].*",
                ErrorCode.PASSWORD_AT_LEAST_ONE_LOWERCASE,
                "Password must contain at least one lowercase letter.");
    }

    private static void validateDigit(String password) {
        validatePattern(password,
                ParameterCode.PASSWORD_AT_LEAST_ONE_DIGIT_CONTROL,
                ".*\\d.*",
                ErrorCode.PASSWORD_AT_LEAST_ONE_DIGIT,
                "Password must contain at least one digit.");
    }

    private static void validateSpecialCharacter(String password) {
        validatePattern(password,
                ParameterCode.PASSWORD_AT_LEAST_ONE_SPECIAL_CHARACTER_CONTROL,
                ".*[!@#$%^&*(),.?\":{}|<>].*",
                ErrorCode.PASSWORD_AT_LEAST_ONE_SPECIAL_CHARACTER,
                "Password must contain at least one special character.");
    }

    private static void validatePattern(String password, ParameterCode paramCode, String regex, ErrorCode errorCode, String message) {
        if (isActive(paramCode) && !password.matches(regex)) {
            throw new InternalServerException(errorCode, message);
        }
    }

    private static void validatePreviousPasswords(PasswordEncoder passwordEncoder, User user, String password) {
        if (!isActive(ParameterCode.LAST_3_PREVIOUS_PASSWORD_DIFFERENT_CONTROL) || Objects.isNull(user)) return;

        boolean reused = Stream.of(user.getPassword(), user.getPassword2(), user.getPassword3())
                .filter(Objects::nonNull)
                .anyMatch(oldPass -> passwordEncoder.matches(password, oldPass));

        if (reused) {
            throw new InternalServerException(
                    ErrorCode.LAST_3_PREVIOUS_PASSWORD_DIFFERENT,
                    "Your new password must be different from your last 3 passwords."
            );
        }
    }

    private static boolean isActive(ParameterCode code) {
        return Status.ACTIVE.equals(ParameterCache.getParamValueAsStatus(code));
    }
}
