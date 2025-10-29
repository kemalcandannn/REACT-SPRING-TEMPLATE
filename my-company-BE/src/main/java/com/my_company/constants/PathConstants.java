package com.my_company.constants;

public class PathConstants {
    private PathConstants() {
        throw new UnsupportedOperationException(ApplicationConstants.UTILITY_CLASS);
    }

    public static final String API_V1_AUTHENTICATION_URL = "/api/v1/authentication";
    public static final String API_V1_AUTHENTICATION_ALL_URL = "/api/v1/authentication/**";
    public static final String LOGIN_URL = "/login";
    public static final String LOCAL_SIGN_UP_URL = "/local-sign-up";
    public static final String VERIFY_ACCOUNT_URL = "/verify-account";
    public static final String SEND_PASSWORD_RESET_LINK_URL = "/send-password-reset-link";
    public static final String RESET_PASSWORD_URL = "/reset-password";
    public static final String API_V1_AUTHENTICATION_LOGIN_URL = API_V1_AUTHENTICATION_URL + LOGIN_URL;
    public static final String API_V1_AUTHENTICATION_LOCAL_SIGN_UP_URL = API_V1_AUTHENTICATION_URL + LOCAL_SIGN_UP_URL;
    public static final String API_V1_AUTHENTICATION_VERIFY_ACCOUNT_URL = API_V1_AUTHENTICATION_URL + VERIFY_ACCOUNT_URL;
    public static final String API_V1_AUTHENTICATION_SEND_PASSWORD_RESET_LINK_URL = API_V1_AUTHENTICATION_URL + SEND_PASSWORD_RESET_LINK_URL;
    public static final String API_V1_AUTHENTICATION_RESET_PASSWORD_URL = API_V1_AUTHENTICATION_URL + RESET_PASSWORD_URL;
    public static final String SWAGGER_UI_ALL_URL = "/swagger-ui/**";
    public static final String V3_API_DOCS_ALL_URL = "/v3/api-docs/**";
    public static final String API_V1_USER_URL = "/api/v1/user";
    public static final String API_V1_USER_ALL_URL = "/api/v1/user/**";
    public static final String API_V1_ROLE_URL = "/api/v1/role";
    public static final String API_V1_ROLE_ALL_URL = "/api/v1/role/**";
    public static final String API_V1_MENU_URL = "/api/v1/menu";
    public static final String API_V1_MENU_ALL_URL = "/api/v1/menu/**";
    public static final String API_V1_USER_ROLE_URL = "/api/v1/user-role";
    public static final String API_V1_USER_ROLE_ALL_URL = "/api/v1/user-role/**";
    public static final String API_V1_USER_MENU_URL = "/api/v1/user-menu";
    public static final String API_V1_USER_MENU_ALL_URL = "/api/v1/user-menu/**";
    public static final String API_V1_ROLE_MENU_URL = "/api/v1/role-menu";
    public static final String API_V1_ROLE_MENU_ALL_URL = "/api/v1/role-menu/**";
    public static final String API_V1_PARAMETER_URL = "/api/v1/parameter";
    public static final String API_V1_PARAMETER_ALL_URL = "/api/v1/parameter/**";
}
