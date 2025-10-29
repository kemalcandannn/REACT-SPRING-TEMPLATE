package com.my_company.controller.authentication;

import com.my_company.constants.PathConstants;
import com.my_company.constants.TextConstants;
import com.my_company.domain.dto.authentication.UserRoleDTO;
import com.my_company.domain.response.ServiceResponse;
import com.my_company.service.authentication.UserRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PathConstants.API_V1_USER_ROLE_URL)
@Slf4j
@RequiredArgsConstructor
@Tag(name = TextConstants.USER_ROLE, description = TextConstants.CRUD_SERVICES_FOR + TextConstants.USER_ROLE)
public class UserRoleController {
    private final UserRoleService userRoleService;

    @GetMapping(value = "/find-all")
    @Operation(summary = "List All", description = "Find All Records")
    public ResponseEntity<ServiceResponse<List<UserRoleDTO>>> findAll() {
        List<UserRoleDTO> userRoleDTOList = userRoleService.findAll();
        return ResponseEntity.ok(
                ServiceResponse
                        .<List<UserRoleDTO>>builder()
                        .data(userRoleDTOList)
                        .build());
    }

    @GetMapping(value = "/find-all-with-pageable")
    @Operation(summary = "List All Records With Pageable", description = "Find All Records With Paging Parameters")
    public ResponseEntity<ServiceResponse<Page<UserRoleDTO>>> findAllWithPageable(@PageableDefault Pageable pageable) {
        Page<UserRoleDTO> userRoleDTOPage = userRoleService.findAllWithPageable(pageable);
        return ResponseEntity.ok(
                ServiceResponse
                        .<Page<UserRoleDTO>>builder()
                        .data(userRoleDTOPage)
                        .build());
    }

    @GetMapping(value = "/find/user/{username}/role/{roleCode}")
    @Operation(summary = "Find by ID", description = "Find Record By ID")
    public ResponseEntity<ServiceResponse<UserRoleDTO>> findByUsernameAndRoleCode(@PathVariable String username, @PathVariable String roleCode) {
        UserRoleDTO userRoleDTO = userRoleService.findByUsernameAndRoleCode(username, roleCode, true);
        return ResponseEntity.ok(
                ServiceResponse
                        .<UserRoleDTO>builder()
                        .data(userRoleDTO)
                        .build());
    }

    @PostMapping(value = "/save-or-update")
    @Operation(summary = "Save or Update Record", description = "Save or Update Record by Request Body")
    public ResponseEntity<ServiceResponse<UserRoleDTO>> saveOrUpdate(@Valid @RequestBody UserRoleDTO dto) {
        UserRoleDTO userRoleDTO = userRoleService.saveOrUpdate(dto);
        return ResponseEntity.ok(
                ServiceResponse
                        .<UserRoleDTO>builder()
                        .data(userRoleDTO)
                        .build());
    }

    @DeleteMapping(value = "/delete/user/{username}/role/{roleCode}")
    @Operation(summary = "Delete by ID", description = "Delete Record By ID")
    public ResponseEntity<ServiceResponse<Boolean>> deleteByUsernameAndRoleCode(@PathVariable String username, @PathVariable String roleCode) {
        userRoleService.deleteByUsernameAndRoleCode(username, roleCode);
        return ResponseEntity.ok(
                ServiceResponse
                        .<Boolean>builder()
                        .data(true)
                        .build());
    }
}
