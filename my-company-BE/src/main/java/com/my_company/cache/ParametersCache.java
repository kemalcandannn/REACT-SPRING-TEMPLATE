package com.my_company.cache;

import com.my_company.constants.ApplicationConstants;
import com.my_company.constants.enums.ParameterCode;
import com.my_company.constants.enums.ParameterType;
import com.my_company.constants.enums.Status;
import com.my_company.domain.dto.system.ParametersDTO;
import com.my_company.utils.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ParametersCache {
    private ParametersCache() {
        throw new UnsupportedOperationException(ApplicationConstants.UTILITY_CLASS);
    }

    // Thread-safe static map (code -> ParameterDTO)
    private static final Map<String, ParametersDTO> PARAMETERS_MAP = new ConcurrentHashMap<>();

    // Get all parameters as a list
    public static List<ParametersDTO> getParameters() {
        return Collections.unmodifiableList(new ArrayList<>(PARAMETERS_MAP.values()));
    }

    // Get a parameter by its code
    public static ParametersDTO getParameter(String code) {
        if (StringUtils.isNullOrBlank(code)) return null;
        return PARAMETERS_MAP.get(code);
    }

    // Get a parameter by its enum
    public static ParametersDTO getParameter(ParameterCode parameterCode) {
        if (parameterCode == null) return null;
        return getParameter(parameterCode.name());
    }

    // Add a parameter (update if it already exists)
    public static void addParameter(ParametersDTO parametersDTO) {
        if (parametersDTO == null || StringUtils.isNullOrBlank(parametersDTO.getCode())) return;
        PARAMETERS_MAP.put(parametersDTO.getCode(), parametersDTO);
    }

    // Remove a parameter
    public static void removeParameter(String code) {
        if (StringUtils.isNullOrBlank(code)) return;
        PARAMETERS_MAP.remove(code);
    }

    // Completely refresh all parameters
    public static void refreshParameters(List<ParametersDTO> parameterList) {
        PARAMETERS_MAP.clear();
        if (parameterList != null) {
            parameterList.forEach(p -> PARAMETERS_MAP.put(p.getCode(), p));
        }
    }

    public static String getParamValueAsString(ParameterCode parameterCode) {
        if (parameterCode == null) {
            return null;
        }

        ParametersDTO parametersDTO = ParametersCache.getParameter(parameterCode);

        if (parametersDTO == null) {
            return null;
        }

        return parametersDTO.getValue();
    }

    public static Integer getParamValueAsInteger(ParameterCode parameterCode) {
        if (parameterCode == null) {
            return null;
        }

        ParametersDTO parametersDTO = ParametersCache.getParameter(parameterCode);

        if (parametersDTO == null || !ParameterType.NUMERIC.equals(parametersDTO.getType())) {
            return null;
        }

        return Integer.valueOf(parametersDTO.getValue());
    }

    public static BigDecimal getParamValueAsBigDecimal(ParameterCode parameterCode) {
        if (parameterCode == null) {
            return null;
        }

        ParametersDTO parametersDTO = ParametersCache.getParameter(parameterCode);

        if (parametersDTO == null || !ParameterType.NUMERIC.equals(parametersDTO.getType())) {
            return null;
        }

        return new BigDecimal(parametersDTO.getValue());
    }

    public static Status getParamValueAsStatus(ParameterCode parameterCode) {
        if (parameterCode == null) {
            return null;
        }

        ParametersDTO parametersDTO = ParametersCache.getParameter(parameterCode);

        if (parametersDTO == null || !ParameterType.STATUS.equals(parametersDTO.getType())) {
            return null;
        }

        return Status.getStatus(parametersDTO.getValue());
    }
}
