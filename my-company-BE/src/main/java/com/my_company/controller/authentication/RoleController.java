package com.my_company.controller.authentication;

import com.my_company.constants.PathConstants;
import com.my_company.constants.TextConstants;
import com.my_company.controller.BaseController;
import com.my_company.domain.dto.authentication.RoleDTO;
import com.my_company.service.authentication.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PathConstants.API_V1_ROLE_URL)
@Slf4j
@Tag(name = TextConstants.ROLE, description = TextConstants.CRUD_SERVICES_FOR + TextConstants.ROLE)
public class RoleController extends BaseController<RoleDTO, String> {

    public RoleController(RoleService service) {
        super(service);
    }

}