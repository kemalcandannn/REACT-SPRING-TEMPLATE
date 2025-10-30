package com.my_company.constants;

public class TextConstants {
    private TextConstants() {
        throw new UnsupportedOperationException(ApplicationConstants.UTILITY_CLASS);
    }

    public static final String CRUD_SERVICES_FOR = "CRUD Services for ";

    public static final String AUTHENTICATION = "Authentication";
    public static final String USER = "User";
    public static final String ROLE = "Role";
    public static final String MENU = "Menu";
    public static final String USER_ROLE = "User-Role";
    public static final String USER_MENU = "User-Menu";
    public static final String ROLE_MENU = "Role-Menu";
    public static final String PARAMETER = "Parameter";

    public static final String REQUIRED_FIELD_MESSAGE = "[%s] is a required field, please check it.";
    public static final String RECORD_WITH_ID_NOT_FOUND_MESSAGE = "Record with ID [%s] was not found.";
    public static final String DO_NOT_HAVE_PERMISSION_MESSAGE = "You do not have permission to perform the requested operation.";

    public static final String INCORRECT_USERNAME_OR_PASSWORD_MESSAGE = "Incorrect username or password, please check the information you entered.";
    public static final String USED_BY_THE_SYSTEM_MESSAGE = "[%s] is used by the system.";
}
