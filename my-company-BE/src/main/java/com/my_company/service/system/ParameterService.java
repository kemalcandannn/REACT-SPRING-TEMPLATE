package com.my_company.service.system;

import com.my_company.cache.ParameterCache;
import com.my_company.domain.dto.system.ParameterDTO;
import com.my_company.domain.entity.system.Parameter;
import com.my_company.mapper.system.ParameterMapper;
import com.my_company.repository.system.ParameterRepository;
import com.my_company.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
