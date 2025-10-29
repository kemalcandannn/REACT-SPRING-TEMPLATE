package com.my_company.controller.authentication;

import com.my_company.constants.PathConstants;
import com.my_company.constants.TextConstants;
import com.my_company.domain.dto.authentication.UserMenuDTO;
import com.my_company.domain.response.ServiceResponse;
import com.my_company.service.authentication.UserMenuService;
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
@RequestMapping(PathConstants.API_V1_USER_MENU_URL)
@Slf4j
@RequiredArgsConstructor
@Tag(name = TextConstants.USER_MENU, description = TextConstants.CRUD_SERVICES_FOR + TextConstants.USER_MENU)
public class UserMenuController {
    private final UserMenuService userMenuService;

    @GetMapping(value = "/find-all")
    @Operation(summary = "List All", description = "Find All Records")
    public ResponseEntity<ServiceResponse<List<UserMenuDTO>>> findAll() {
        List<UserMenuDTO> userMenuDTOList = userMenuService.findAll();
        return ResponseEntity.ok(
                ServiceResponse
                        .<List<UserMenuDTO>>builder()
                        .data(userMenuDTOList)
                        .build());
    }

    @GetMapping(value = "/find-all-with-pageable")
    @Operation(summary = "List All Records With Pageable", description = "Find All Records With Paging Parameters")
    public ResponseEntity<ServiceResponse<Page<UserMenuDTO>>> findAllWithPageable(@PageableDefault Pageable pageable) {
        Page<UserMenuDTO> userMenuDTOPage = userMenuService.findAllWithPageable(pageable);
        return ResponseEntity.ok(
                ServiceResponse
                        .<Page<UserMenuDTO>>builder()
                        .data(userMenuDTOPage)
                        .build());
    }

    @GetMapping(value = "/find/user/{username}/menu/{menuCode}")
    @Operation(summary = "Find by ID", description = "Find Record By ID")
    public ResponseEntity<ServiceResponse<UserMenuDTO>> findByUsernameAndMenuCode(@PathVariable String username, @PathVariable String menuCode) {
        UserMenuDTO userMenuDTO = userMenuService.findByUsernameAndMenuCode(username, menuCode, true);
        return ResponseEntity.ok(
                ServiceResponse
                        .<UserMenuDTO>builder()
                        .data(userMenuDTO)
                        .build());
    }

    @PostMapping(value = "/save-or-update")
    @Operation(summary = "Save or Update Record", description = "Save or Update Record by Request Body")
    public ResponseEntity<ServiceResponse<UserMenuDTO>> saveOrUpdate(@Valid @RequestBody UserMenuDTO dto) {
        UserMenuDTO userMenuDTO = userMenuService.saveOrUpdate(dto);
        return ResponseEntity.ok(
                ServiceResponse
                        .<UserMenuDTO>builder()
                        .data(userMenuDTO)
                        .build());
    }

    @DeleteMapping(value = "/delete/user/{username}/menu/{menuCode}")
    @Operation(summary = "Delete by ID", description = "Delete Record By ID")
    public ResponseEntity<ServiceResponse<Boolean>> deleteByUsernameAndRoleCode(@PathVariable String username, @PathVariable String menuCode) {
        userMenuService.deleteByUsernameAndMenuCode(username, menuCode);
        return ResponseEntity.ok(
                ServiceResponse
                        .<Boolean>builder()
                        .data(true)
                        .build());
    }
}
