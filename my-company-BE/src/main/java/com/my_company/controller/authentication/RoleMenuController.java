package com.my_company.controller.authentication;

import com.my_company.constants.PathConstants;
import com.my_company.constants.TextConstants;
import com.my_company.controller.BaseController;
import com.my_company.domain.dto.authentication.RoleMenuDTO;
import com.my_company.domain.entity.authentication.RoleMenuId;
import com.my_company.service.authentication.RoleMenuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PathConstants.API_V1_ROLE_MENU_URL)
@Slf4j
@Tag(name = TextConstants.ROLE_MENU, description = TextConstants.CRUD_SERVICES_FOR + TextConstants.ROLE_MENU)
public class RoleMenuController extends BaseController<RoleMenuDTO, RoleMenuId> {

    public RoleMenuController(RoleMenuService service) {
        super(service);
    }

}