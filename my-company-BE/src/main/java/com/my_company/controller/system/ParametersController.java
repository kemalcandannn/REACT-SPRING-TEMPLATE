package com.my_company.controller.system;

import com.my_company.cache.ParametersCache;
import com.my_company.constants.PathConstants;
import com.my_company.constants.TextConstants;
import com.my_company.controller.BaseController;
import com.my_company.domain.dto.system.ParametersDTO;
import com.my_company.domain.response.ServiceResponse;
import com.my_company.service.system.ParametersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PathConstants.API_V1_PARAMETERS_URL)
@Slf4j
@Tag(name = TextConstants.PARAMETERS, description = TextConstants.CRUD_SERVICES_FOR + TextConstants.PARAMETERS)
public class ParametersController extends BaseController<ParametersDTO, String> {
    public ParametersController(ParametersService service) {
        super(service);
    }

    @GetMapping(value = "/find-all-from-cache")
    @Operation(summary = "List All From Cache", description = "Find All Parameters From Cache")
    public ResponseEntity<ServiceResponse<List<ParametersDTO>>> findAllFromCache() {
        return ResponseEntity.ok(
                ServiceResponse
                        .<List<ParametersDTO>>builder()
                        .data(ParametersCache.getParameters())
                        .build());
    }

    @GetMapping(value = "/find/{code}")
    @Operation(summary = "Find by Code", description = "Find Record By Code")
    @Override
    public ResponseEntity<ServiceResponse<ParametersDTO>> findById(@PathVariable String code) {
        return super.findById(code);
    }

    @DeleteMapping(value = "/delete/{code}")
    @Operation(summary = "Delete by Code", description = "Delete Record By Code")
    @Override
    public ResponseEntity<ServiceResponse<Boolean>> deleteById(@PathVariable String code) {
        return super.deleteById(code);
    }
}
