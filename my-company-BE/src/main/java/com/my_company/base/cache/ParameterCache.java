package com.my_company.base.cache;

import com.my_company.base.constants.ApplicationConstants;
import com.my_company.base.constants.enums.ParameterCode;
import com.my_company.base.domain.dto.system.ParameterDTO;
import com.my_company.base.utils.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class ParameterCache {

    private ParameterCache() {
        throw new UnsupportedOperationException(ApplicationConstants.UTILITY_CLASS);
    }

    // Thread-safe static liste
    private static final List<ParameterDTO> PARAMETERS = new CopyOnWriteArrayList<>();

    public static List<ParameterDTO> getParameters() {
        return PARAMETERS;
    }

    public static ParameterDTO getParameter(ParameterCode parameterCode) {
        return ParameterCache
                .getParameters()
                .stream()
                .filter(p -> p.getCode().equals(parameterCode.name()))
                .findFirst()
                .orElse(null);
    }

    public static ParameterDTO getParameter(String code) {
        return ParameterCache
                .getParameters()
                .stream()
                .filter(p -> p.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

    public static void addParameter(ParameterDTO parameterDTO) {
        if (Objects.isNull(parameterDTO) ||
                StringUtils.isNullOrBlank(parameterDTO.getCode()) ||
                PARAMETERS
                        .stream()
                        .anyMatch(p -> p.getCode().equals(parameterDTO.getCode()))) {
            return;
        }

        PARAMETERS.add(parameterDTO);
    }

    public static void removeParameter(String code) {
        PARAMETERS.removeIf(p -> p.getCode().equals(code));
    }
}