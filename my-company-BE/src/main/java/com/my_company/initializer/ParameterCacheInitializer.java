package com.my_company.initializer;

import com.my_company.cache.ParametersCache;
import com.my_company.domain.dto.system.ParametersDTO;
import com.my_company.service.system.ParametersService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ParameterCacheInitializer {
    private final ParametersService parametersService;

    @PostConstruct
    public void init() {
        List<ParametersDTO> parametersDTOList = parametersService.findAll();
        ParametersCache.refreshParameters(parametersDTOList);
    }
}
