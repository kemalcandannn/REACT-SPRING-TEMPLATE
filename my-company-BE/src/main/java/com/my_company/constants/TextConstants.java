package com.my_company.constants;

public class TextConstants {

    private TextConstants() {
        throw new UnsupportedOperationException(ApplicationConstants.UTILITY_CLASS);
    }

    public static final String INCORRECT_USERNAME_OR_PASSWORD_MESSAGE = "Incorrect username or password, please check the information you entered.";
    public static final String TOKEN_EXPIRED_MESSAGE = "Token has expired. Please log out/log in and try again.";
    public static final String REQUIRED_FIELD_MESSAGE = "[%s] is a required field, please check it.";
    public static final String RECORD_WITH_ID_NOT_FOUND_MESSAGE = "Record with ID [%s] was not found.";
    public static final String USERNAME_ALREADY_REGISTERED_MESSAGE = "The username is already registered in the system.";
    public static final String PASSWORD_AT_LEAST_CHARACTERS_LONG_MESSAGE = "Password must be at least [%d] characters long.";
    public static final String PASSWORD_AT_LEAST_ONE_UPPERCASE_MESSAGE = "Password must contain at least one uppercase letter.";
    public static final String PASSWORD_AT_LEAST_ONE_LOWERCASE_MESSAGE = "Password must contain at least one lowercase letter.";
    public static final String PASSWORD_AT_LEAST_ONE_DIGIT_MESSAGE = "Password must contain at least one digit.";
    public static final String PASSWORD_AT_LEAST_ONE_SPECIAL_CHARACTER_MESSAGE = "Password must contain at least one special character.";
    public static final String CRUD_SERVICES_FOR = "CRUD Services for ";

    public static final String AUTHENTICATION = "Authentication";
    public static final String USER = "User";
    public static final String ROLE = "Role";
    public static final String MENU = "Menu";
    public static final String USER_ROLE = "User-Role";
    public static final String ROLE_MENU = "Role-Menu";
    public static final String PARAMETER = "Parameter";

}
