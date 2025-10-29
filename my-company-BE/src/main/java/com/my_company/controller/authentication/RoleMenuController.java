package com.my_company.controller.authentication;

import com.my_company.constants.PathConstants;
import com.my_company.constants.TextConstants;
import com.my_company.domain.dto.authentication.RoleMenuDTO;
import com.my_company.domain.response.ServiceResponse;
import com.my_company.service.authentication.RoleMenuService;
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
@RequestMapping(PathConstants.API_V1_ROLE_MENU_URL)
@Slf4j
@RequiredArgsConstructor
@Tag(name = TextConstants.ROLE_MENU, description = TextConstants.CRUD_SERVICES_FOR + TextConstants.ROLE_MENU)
public class RoleMenuController {
    private final RoleMenuService roleMenuService;

    @GetMapping(value = "/find-all")
    @Operation(summary = "List All", description = "Find All Records")
    public ResponseEntity<ServiceResponse<List<RoleMenuDTO>>> findAll() {
        List<RoleMenuDTO> dtoList = roleMenuService.findAll();
        return ResponseEntity.ok(
                ServiceResponse
                        .<List<RoleMenuDTO>>builder()
                        .data(dtoList)
                        .build());
    }

    @GetMapping(value = "/find-all-with-pageable")
    @Operation(summary = "List All Records With Pageable", description = "Find All Records With Paging Parameters")
    public ResponseEntity<ServiceResponse<Page<RoleMenuDTO>>> findAllWithPageable(@PageableDefault Pageable pageable) {
        Page<RoleMenuDTO> dtoPage = roleMenuService.findAllWithPageable(pageable);
        return ResponseEntity.ok(
                ServiceResponse
                        .<Page<RoleMenuDTO>>builder()
                        .data(dtoPage)
                        .build());
    }

    @GetMapping(value = "/find/role/{roleCode}/menu/{menuCode}")
    @Operation(summary = "Find by ID", description = "Find Record By ID")
    public ResponseEntity<ServiceResponse<RoleMenuDTO>> findByRoleCodeAndMenuCode(@PathVariable String roleCode, @PathVariable String menuCode) {
        RoleMenuDTO dto = roleMenuService.findByRoleCodeAndMenuCode(roleCode, menuCode, true);
        return ResponseEntity.ok(
                ServiceResponse
                        .<RoleMenuDTO>builder()
                        .data(dto)
                        .build());
    }

    @PostMapping(value = "/save-or-update")
    @Operation(summary = "Save or Update Record", description = "Save or Update Record by Request Body")
    public ResponseEntity<ServiceResponse<RoleMenuDTO>> saveOrUpdate(@Valid @RequestBody RoleMenuDTO dto) {
        RoleMenuDTO responseDto = roleMenuService.saveOrUpdate(dto);
        return ResponseEntity.ok(
                ServiceResponse
                        .<RoleMenuDTO>builder()
                        .data(responseDto)
                        .build());
    }

    @DeleteMapping(value = "/delete/role/{roleCode}/menu/{menuCode}")
    @Operation(summary = "Delete by ID", description = "Delete Record By ID")
    public ResponseEntity<ServiceResponse<Boolean>> deleteByRoleCodeAndMenuCode(@PathVariable String roleCode, @PathVariable String menuCode) {
        roleMenuService.deleteByRoleCodeAndMenuCode(roleCode, menuCode);
        return ResponseEntity.ok(
                ServiceResponse
                        .<Boolean>builder()
                        .data(true)
                        .build());
    }
}
