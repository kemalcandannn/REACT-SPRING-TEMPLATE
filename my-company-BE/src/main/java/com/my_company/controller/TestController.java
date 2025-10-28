package com.my_company.controller;

import com.my_company.constants.PathConstants;
import com.my_company.domain.response.ServiceResponse;
import com.my_company.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(PathConstants.API_V1_TEST_URL)
@RequiredArgsConstructor
@Slf4j
public class TestController {
    private final TestService testService;

    @GetMapping(value = "/random-integer-list")
    public ResponseEntity<ServiceResponse<List<Integer>>> getRandomIntegerList() {
        return ResponseEntity.ok(
                ServiceResponse
                        .<List<Integer>>builder()
                        .success(true)
                        .statusCode(HttpStatus.OK.value())
                        .data(testService.getRandomIntegerList())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}
