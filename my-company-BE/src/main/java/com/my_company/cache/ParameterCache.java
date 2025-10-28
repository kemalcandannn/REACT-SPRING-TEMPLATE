package com.my_company.cache;

import com.my_company.constants.ApplicationConstants;
import com.my_company.constants.enums.ParameterCode;
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

    // Tüm parametreleri liste olarak almak için
    public static List<ParameterDTO> getParameters() {
        return Collections.unmodifiableList(new ArrayList<>(PARAMETERS_MAP.values()));
    }

    // Parametreyi code üzerinden al
    public static ParameterDTO getParameter(String code) {
        if (StringUtils.isNullOrBlank(code)) return null;
        return PARAMETERS_MAP.get(code);
    }

    // Parametreyi enum üzerinden al
    public static ParameterDTO getParameter(ParameterCode parameterCode) {
        if (parameterCode == null) return null;
        return getParameter(parameterCode.name());
    }

    // Parametre ekle (varsa eklemez)
    public static void addParameter(ParameterDTO parameterDTO) {
        if (parameterDTO == null || StringUtils.isNullOrBlank(parameterDTO.getCode())) return;
        PARAMETERS_MAP.put(parameterDTO.getCode(), parameterDTO);
    }

    // Parametreyi sil
    public static void removeParameter(String code) {
        if (StringUtils.isNullOrBlank(code)) return;
        PARAMETERS_MAP.remove(code);
    }

    // Parametreleri tamamen yenile (refresh)
    public static void refreshParameters(List<ParameterDTO> parameterList) {
        PARAMETERS_MAP.clear();
        if (parameterList != null) {
            parameterList.forEach(p -> PARAMETERS_MAP.put(p.getCode(), p));
        }
    }
}
