package com.my_company.controller.system;

import com.my_company.cache.ParameterCache;
import com.my_company.constants.PathConstants;
import com.my_company.constants.TextConstants;
import com.my_company.controller.BaseController;
import com.my_company.domain.dto.system.ParameterDTO;
import com.my_company.domain.response.ServiceResponse;
import com.my_company.service.system.ParameterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(PathConstants.API_V1_PARAMETER_URL)
@Slf4j
@Tag(name = TextConstants.PARAMETER, description = TextConstants.CRUD_SERVICES_FOR + TextConstants.PARAMETER)
public class ParameterController extends BaseController<ParameterDTO, String> {

    public ParameterController(ParameterService service) {
        super(service);
    }

    @GetMapping(value = "/get-parameters")
    @Operation(summary = "List All From Cache", description = "Find All Parameters From Cache")
    public ResponseEntity<ServiceResponse<List<ParameterDTO>>> findAllFromCache() {
        return ResponseEntity.ok(
                ServiceResponse
                        .<List<ParameterDTO>>builder()
                        .success(true)
                        .statusCode(HttpStatus.OK.value())
                        .data(ParameterCache.getParameters())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

}