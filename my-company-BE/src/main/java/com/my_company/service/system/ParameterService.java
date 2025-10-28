package com.my_company.service.system;

import com.my_company.cache.ParameterCache;
import com.my_company.constants.enums.ParameterCode;
import com.my_company.constants.enums.ParameterType;
import com.my_company.constants.enums.Status;
import com.my_company.domain.dto.system.ParameterDTO;
import com.my_company.domain.entity.system.Parameter;
import com.my_company.mapper.system.ParameterMapper;
import com.my_company.repository.system.ParameterRepository;
import com.my_company.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class ParameterService extends BaseService<Parameter, ParameterDTO, String> {

    public ParameterService(ParameterRepository repository, ParameterMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public ParameterDTO saveOrUpdate(ParameterDTO dto) {
        ParameterDTO parameterDTO = super.saveOrUpdate(dto);
        ParameterCache.addParameter(parameterDTO);
        return parameterDTO;
    }

    @Override
    public void deleteById(String code) {
        super.deleteById(code);
        ParameterCache.removeParameter(code);
    }

    public String getParamValueAsString(ParameterCode parameterCode) {
        if (parameterCode == null) {
            return null;
        }

        ParameterDTO parameterDTO = ParameterCache.getParameter(parameterCode);

        if (parameterDTO == null) {
            return null;
        }

        return parameterDTO.getValue();
    }

    public Integer getParamValueAsInteger(ParameterCode parameterCode) {
        if (parameterCode == null) {
            return null;
        }

        ParameterDTO parameterDTO = ParameterCache.getParameter(parameterCode);

        if (parameterDTO == null || !ParameterType.NUMERIC.equals(parameterDTO.getType())) {
            return null;
        }

        return Integer.valueOf(parameterDTO.getValue());
    }

    public BigDecimal getParamValueAsBigDecimal(ParameterCode parameterCode) {
        if (parameterCode == null) {
            return null;
        }

        ParameterDTO parameterDTO = ParameterCache.getParameter(parameterCode);

        if (parameterDTO == null || !ParameterType.NUMERIC.equals(parameterDTO.getType())) {
            return null;
        }

        return new BigDecimal(parameterDTO.getValue());
    }

    public Status getParamValueAsStatus(ParameterCode parameterCode) {
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
