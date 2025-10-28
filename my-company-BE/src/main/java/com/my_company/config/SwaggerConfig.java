package com.my_company.config;

import com.my_company.constants.ApplicationConstants;
import com.my_company.constants.PathConstants;
import com.my_company.constants.TextConstants;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";
    private static final String OPEN_API_TITLE = "My Company API";
    private static final String OPEN_API_DESCRIPTION = "Backend API Documentation";
    private static final String OPEN_API_VERSION = "1.0.0";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(OPEN_API_TITLE)
                        .description(OPEN_API_DESCRIPTION)
                        .version(OPEN_API_VERSION)
                        .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0")))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SECURITY_SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme(ApplicationConstants.BEARER)
                                        .bearerFormat(ApplicationConstants.JWT)));
    }

    @Bean
    public List<GroupedOpenApi> groupedOpenApis() {
        return List.of(
                buildApi(TextConstants.AUTHENTICATION, PathConstants.API_V1_AUTHENTICATION_ALL_URL),
                buildApi(TextConstants.USER, PathConstants.API_V1_USER_ALL_URL),
                buildApi(TextConstants.ROLE, PathConstants.API_V1_ROLE_ALL_URL),
                buildApi(TextConstants.MENU, PathConstants.API_V1_MENU_ALL_URL),
                buildApi(TextConstants.USER_ROLE, PathConstants.API_V1_USER_ROLE_ALL_URL),
                buildApi(TextConstants.ROLE_MENU, PathConstants.API_V1_ROLE_MENU_ALL_URL),
                buildApi(TextConstants.PARAMETER, PathConstants.API_V1_PARAMETER_ALL_URL)
        );
    }

    private GroupedOpenApi buildApi(String groupName, String... paths) {
        return GroupedOpenApi.builder()
                .group(groupName)
                .pathsToMatch(paths)
                .build();
    }

}
