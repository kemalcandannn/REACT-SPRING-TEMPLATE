package com.my_company.base.controller.system;

import com.my_company.base.constants.PathConstants;
import com.my_company.base.constants.TextConstants;
import com.my_company.base.controller.BaseController;
import com.my_company.base.domain.dto.system.ParameterDTO;
import com.my_company.base.service.system.ParameterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PathConstants.API_V1_PARAMETER_URL)
@Slf4j
@Tag(name = TextConstants.PARAMETER, description = TextConstants.CRUD_SERVICES_FOR + TextConstants.PARAMETER)
public class ParameterController extends BaseController<ParameterDTO, String> {

    public ParameterController(ParameterService service) {
        super(service);
    }

}