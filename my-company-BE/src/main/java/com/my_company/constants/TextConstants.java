package com.my_company.constants;

public class TextConstants {
    private TextConstants() {
        throw new UnsupportedOperationException(ApplicationConstants.UTILITY_CLASS);
    }

    public static final String INCORRECT_USERNAME_MESSAGE = "The username is not found in the system.";
    public static final String INCORRECT_USERNAME_OR_PASSWORD_MESSAGE = "Incorrect username or password, please check the information you entered.";
    public static final String INCORRECT_OLD_PASSWORD_MESSAGE = "Incorrect old password.";
    public static final String TOKEN_EXPIRED_MESSAGE = "Token has expired. Please log out/log in and try again.";
    public static final String TOKEN_VALIDATION_ERROR_MESSAGE = "Token Validation Error";
    public static final String DO_NOT_HAVE_PERMISSION_MESSAGE = "You do not have permission to perform the requested operation.";
    public static final String REQUIRED_FIELD_MESSAGE = "[%s] is a required field, please check it.";
    public static final String RECORD_WITH_ID_NOT_FOUND_MESSAGE = "Record with ID [%s] was not found.";
    public static final String USED_BY_THE_SYSTEM_MESSAGE = "[%s] is used by the system.";
    public static final String USERNAME_ALREADY_REGISTERED_MESSAGE = "The username is already registered in the system.";
    public static final String NEW_PASSWORD_DOES_NOT_CONFIRM_MESSAGE = "New Password does not confirm.";
    public static final String PASSWORD_AT_LEAST_CHARACTERS_LONG_MESSAGE = "Password must be at least [%d] characters long.";
    public static final String PASSWORD_AT_LEAST_ONE_UPPERCASE_MESSAGE = "Password must contain at least one uppercase letter.";
    public static final String PASSWORD_AT_LEAST_ONE_LOWERCASE_MESSAGE = "Password must contain at least one lowercase letter.";
    public static final String PASSWORD_AT_LEAST_ONE_DIGIT_MESSAGE = "Password must contain at least one digit.";
    public static final String PASSWORD_AT_LEAST_ONE_SPECIAL_CHARACTER_MESSAGE = "Password must contain at least one special character.";
    public static final String LAST_3_PREVIOUS_PASSWORD_DIFFERENT_MESSAGE = "Your new password must be different from your last 3 passwords.";
    public static final String CRUD_SERVICES_FOR = "CRUD Services for ";

    public static final String AUTHENTICATION = "Authentication";
    public static final String USER = "User";
    public static final String ROLE = "Role";
    public static final String MENU = "Menu";
    public static final String USER_ROLE = "User-Role";
    public static final String USER_MENU = "User-Menu";
    public static final String ROLE_MENU = "Role-Menu";
    public static final String PARAMETER = "Parameter";
}
