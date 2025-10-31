package com.my_company.controller.authentication;

import com.my_company.constants.PathConstants;
import com.my_company.constants.TextConstants;
import com.my_company.controller.BaseController;
import com.my_company.domain.dto.authentication.UserDTO;
import com.my_company.domain.response.ServiceResponse;
import com.my_company.service.authentication.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PathConstants.API_V1_USER_URL)
@Slf4j
@Tag(name = TextConstants.USER, description = TextConstants.CRUD_SERVICES_FOR + TextConstants.USER)
public class UserController extends BaseController<UserDTO, String> {

    public UserController(UserService service) {
        super(service);
    }

    @GetMapping(value = "/find/{username}")
    @Operation(summary = "Find by ID", description = "Find Record By ID")
    @Override
    public ResponseEntity<ServiceResponse<UserDTO>> findById(@PathVariable String username) {
        return super.findById(username);
    }

    @Hidden
    @Override
    public ResponseEntity<ServiceResponse<UserDTO>> saveOrUpdate(UserDTO dto) {
        return super.saveOrUpdate(dto);
    }

    @Hidden
    @Override
    public ResponseEntity<ServiceResponse<Boolean>> deleteById(String s) {
        return super.deleteById(s);
    }
}
