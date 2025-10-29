package com.my_company.constants;

public class PathConstants {
    private PathConstants() {
        throw new UnsupportedOperationException(ApplicationConstants.UTILITY_CLASS);
    }

    public static final String API_V1_AUTHENTICATION_URL = "/api/v1/authentication";
    public static final String API_V1_AUTHENTICATION_ALL_URL = "/api/v1/authentication/**";
    public static final String LOGIN_URL = "/login";
    public static final String LOCAL_SIGN_UP_URL = "/local-sign-up";
    public static final String FORGOT_PASSWORD_URL = "/forgot-password";
    public static final String API_V1_AUTHENTICATION_LOGIN_URL = API_V1_AUTHENTICATION_URL + LOGIN_URL;
    public static final String API_V1_AUTHENTICATION_LOCAL_SIGN_UP_URL = API_V1_AUTHENTICATION_URL + LOCAL_SIGN_UP_URL;
    public static final String API_V1_AUTHENTICATION_FORGOT_PASSWORD_URL = API_V1_AUTHENTICATION_URL + FORGOT_PASSWORD_URL;
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
    public static final String API_V1_ROLE_MENU_URL = "/api/v1/role-menu";
    public static final String API_V1_ROLE_MENU_ALL_URL = "/api/v1/role-menu/**";
    public static final String API_V1_PARAMETERS_URL = "/api/v1/parameters";
    public static final String API_V1_PARAMETERS_ALL_URL = "/api/v1/parameter/**";
}
