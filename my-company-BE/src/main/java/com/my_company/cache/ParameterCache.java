package com.my_company.cache;

import com.my_company.constants.ApplicationConstants;
import com.my_company.constants.enums.ParameterCode;
import com.my_company.constants.enums.ParameterType;
import com.my_company.constants.enums.Status;
import com.my_company.domain.dto.system.ParameterDTO;
import com.my_company.utils.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ParameterCache {
    private ParameterCache() {
        throw new UnsupportedOperationException(ApplicationConstants.UTILITY_CLASS);
    }

    // Thread-safe static map (code -> ParameterDTO)
    private static final Map<String, ParameterDTO> PARAMETERS_MAP = new ConcurrentHashMap<>();

    // Get all parameters as a list
    public static List<ParameterDTO> getParameters() {
        return Collections.unmodifiableList(new ArrayList<>(PARAMETERS_MAP.values()));
    }

    // Get a parameter by its code
    public static ParameterDTO getParameter(String code) {
        if (StringUtils.isNullOrBlank(code)) return null;
        return PARAMETERS_MAP.get(code);
    }

    // Get a parameter by its enum
    public static ParameterDTO getParameter(ParameterCode parameterCode) {
        if (parameterCode == null) return null;
        return getParameter(parameterCode.name());
    }

    // Add a parameter (update if it already exists)
    public static void addParameter(ParameterDTO parameterDTO) {
        if (parameterDTO == null || StringUtils.isNullOrBlank(parameterDTO.getCode())) return;
        PARAMETERS_MAP.put(parameterDTO.getCode(), parameterDTO);
    }

    // Remove a parameter
    public static void removeParameter(String code) {
        if (StringUtils.isNullOrBlank(code)) return;
        PARAMETERS_MAP.remove(code);
    }

    // Completely refresh all parameters
    public static void refreshParameters(List<ParameterDTO> parameterList) {
        PARAMETERS_MAP.clear();
        if (parameterList != null) {
            parameterList.forEach(p -> PARAMETERS_MAP.put(p.getCode(), p));
        }
    }

    public static Integer getParamValueAsIntegerWithControl(ParameterCode parameterCodeControl, ParameterCode parameterCodeValue) {
        return Status.ACTIVE.equals(getParamValueAsStatus(parameterCodeControl)) ?
                getParamValueAsInteger(parameterCodeValue)
                : null;
    }

    public static Integer getParamValueAsInteger(ParameterCode parameterCode) {
        if (parameterCode == null) {
            return null;
        }

        ParameterDTO parameterDTO = ParameterCache.getParameter(parameterCode);

        if (parameterDTO == null || !ParameterType.NUMERIC.equals(parameterDTO.getType())) {
            return null;
        }

        return Integer.valueOf(parameterDTO.getValue());
    }

    public static Status getParamValueAsStatus(ParameterCode parameterCode) {
        if (parameterCode == null) {
            return null;
        }

        ParameterDTO parameterDTO = ParameterCache.getParameter(parameterCode);

        if (parameterDTO == null || !ParameterType.STATUS.equals(parameterDTO.getType())) {
            return null;
        }

        return Status.getStatus(parameterDTO.getValue());
    }
}
