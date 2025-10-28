package com.my_company.controller.authentication;

import com.my_company.constants.PathConstants;
import com.my_company.constants.TextConstants;
import com.my_company.controller.BaseController;
import com.my_company.domain.dto.authentication.UserRoleDTO;
import com.my_company.domain.entity.authentication.UserRoleId;
import com.my_company.service.authentication.UserRoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PathConstants.API_V1_USER_ROLE_URL)
@Slf4j
@Tag(name = TextConstants.USER_ROLE, description = TextConstants.CRUD_SERVICES_FOR + TextConstants.USER_ROLE)
public class UserRoleController extends BaseController<UserRoleDTO, UserRoleId> {

    public UserRoleController(UserRoleService service) {
        super(service);
    }

}