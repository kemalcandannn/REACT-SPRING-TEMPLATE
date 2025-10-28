package com.my_company.controller;

import com.my_company.domain.dto.BaseDto;
import com.my_company.domain.response.ServiceResponse;
import com.my_company.service.IBaseService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public abstract class BaseController<DTO extends BaseDto<ID>, ID extends Serializable> {
    private final IBaseService<DTO, ID> crudService;

    @GetMapping(value = "/find-all")
    @Operation(summary = "List All", description = "Find All Records")
    public ResponseEntity<ServiceResponse<List<DTO>>> findAll() {
        List<DTO> dtoList = crudService.findAll();
        return ResponseEntity.ok(
                ServiceResponse
                        .<List<DTO>>builder()
                        .success(true)
                        .statusCode(HttpStatus.OK.value())
                        .data(dtoList)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @GetMapping(value = "/find-all-with-pageable")
    @Operation(summary = "List All Records With Pageable", description = "Find All Records With Paging Parameters")
    public ResponseEntity<ServiceResponse<Page<DTO>>> findAllWithPageable(@PageableDefault Pageable pageable) {
        Page<DTO> dtoPage = crudService.findAllWithPageable(pageable);
        return ResponseEntity.ok(
                ServiceResponse
                        .<Page<DTO>>builder()
                        .success(true)
                        .statusCode(HttpStatus.OK.value())
                        .data(dtoPage)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @GetMapping(value = "/find/{id}")
    @Operation(summary = "Find by ID", description = "Find Record By ID")
    public ResponseEntity<ServiceResponse<DTO>> findById(@PathVariable ID id) {
        DTO dto = crudService.findById(id, true);
        return ResponseEntity.ok(
                ServiceResponse
                        .<DTO>builder()
                        .success(true)
                        .statusCode(HttpStatus.OK.value())
                        .data(dto)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @PostMapping(value = "/save-or-update")
    @Operation(summary = "Save or Update Record", description = "Save or Update Record by Request Body")
    public ResponseEntity<ServiceResponse<DTO>> saveOrUpdate(@Valid @RequestBody DTO request) {
        DTO dto = crudService.saveOrUpdate(request);
        return ResponseEntity.ok(
                ServiceResponse
                        .<DTO>builder()
                        .success(true)
                        .statusCode(HttpStatus.OK.value())
                        .data(dto)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @DeleteMapping(value = "/delete/{id}")
    @Operation(summary = "Delete by ID", description = "Delete Record By ID")
    public ResponseEntity<ServiceResponse<Boolean>> deleteById(@PathVariable ID id) {
        crudService.deleteById(id);
        return ResponseEntity.ok(
                ServiceResponse
                        .<Boolean>builder()
                        .success(true)
                        .statusCode(HttpStatus.OK.value())
                        .data(true)
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}
