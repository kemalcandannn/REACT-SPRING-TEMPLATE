package com.my_company.base.initializer;

import com.my_company.base.cache.ParameterCache;
import com.my_company.base.domain.dto.system.ParameterDTO;
import com.my_company.base.service.system.ParameterService;
import com.my_company.base.utils.CollectionUtils;
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

        if (CollectionUtils.isNotEmpty(parameterDTOList)) {
            parameterDTOList.forEach(ParameterCache::addParameter);
        }
    }
}
