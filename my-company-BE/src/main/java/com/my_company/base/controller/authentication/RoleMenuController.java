package com.my_company.base.controller.authentication;

import com.my_company.base.constants.PathConstants;
import com.my_company.base.constants.TextConstants;
import com.my_company.base.controller.BaseController;
import com.my_company.base.domain.dto.authentication.RoleMenuDTO;
import com.my_company.base.domain.entity.authentication.RoleMenuId;
import com.my_company.base.service.authentication.RoleMenuService;
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