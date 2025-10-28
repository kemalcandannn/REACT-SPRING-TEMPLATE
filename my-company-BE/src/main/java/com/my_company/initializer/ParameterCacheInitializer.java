package com.my_company.initializer;

import com.my_company.cache.ParameterCache;
import com.my_company.domain.dto.system.ParameterDTO;
import com.my_company.service.system.ParameterService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ParameterCacheInitializer {

    private final ParameterService parameterService;

    @PostConstruct
    public void init() {
        List<ParameterDTO> parameterDTOList = parameterService.findAll();
        ParameterCache.refreshParameters(parameterDTOList);
    }
}
