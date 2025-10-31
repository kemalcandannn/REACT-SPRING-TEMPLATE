package com.my_company.controller.system;

import com.my_company.cache.ParameterCache;
import com.my_company.constants.PathConstants;
import com.my_company.constants.TextConstants;
import com.my_company.controller.BaseController;
import com.my_company.domain.dto.system.ParameterDTO;
import com.my_company.domain.request.system.UpdateAllParameterRequest;
import com.my_company.domain.response.ServiceResponse;
import com.my_company.service.system.ParameterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PathConstants.API_V1_PARAMETER_URL)
@Slf4j
@Tag(name = TextConstants.PARAMETER, description = TextConstants.CRUD_SERVICES_FOR + TextConstants.PARAMETER)
public class ParameterController extends BaseController<ParameterDTO, String> {
    private final ParameterService service;

    public ParameterController(ParameterService service) {
        super(service);
        this.service = service;
    }

    @GetMapping(value = "/find-all-from-cache")
    @Operation(summary = "List All From Cache", description = "Find All Parameters From Cache")
    public ResponseEntity<ServiceResponse<List<ParameterDTO>>> findAllFromCache() {
        return ResponseEntity.ok(
                ServiceResponse
                        .<List<ParameterDTO>>builder()
                        .data(ParameterCache.getParameters())
                        .build());
    }

    @GetMapping(value = "/find/{code}")
    @Operation(summary = "Find by Code", description = "Find Record By Code")
    @Override
    public ResponseEntity<ServiceResponse<ParameterDTO>> findById(@PathVariable String code) {
        return super.findById(code);
    }

    @DeleteMapping(value = "/delete/{code}")
    @Operation(summary = "Delete by Code", description = "Delete Record By Code")
    @Override
    public ResponseEntity<ServiceResponse<Boolean>> deleteById(@PathVariable String code) {
        return super.deleteById(code);
    }

    @PutMapping(value = "/update-all-parameters")
    @Operation(summary = "Update All Parameters", description = "Update All Parameters by Request Body")
    public ResponseEntity<ServiceResponse<Object>> updateAllParameters(@Valid @RequestBody UpdateAllParameterRequest request) {
        service.updateAllParameters(request);
        return ResponseEntity.ok(
                ServiceResponse
                        .builder()
                        .data(null)
                        .build());
    }
}
