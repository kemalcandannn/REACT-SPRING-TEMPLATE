package com.my_company.service.system;

import com.my_company.cache.ParametersCache;
import com.my_company.constants.TextConstants;
import com.my_company.constants.enums.ErrorCode;
import com.my_company.constants.enums.Role;
import com.my_company.domain.dto.system.ParametersDTO;
import com.my_company.domain.entity.system.Parameters;
import com.my_company.exception.UserAuthenticationException;
import com.my_company.mapper.system.ParametersMapper;
import com.my_company.repository.system.ParametersRepository;
import com.my_company.service.BaseService;
import com.my_company.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ParametersService extends BaseService<Parameters, ParametersDTO, String> {
    public ParametersService(ParametersRepository repository, ParametersMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public ParametersDTO saveOrUpdate(ParametersDTO dto) {
        if (!SecurityUtils.hasRole(Role.SYSTEM_ADMINISTRATOR)) {
            throw new UserAuthenticationException(ErrorCode.UNAUTHORIZED_ACESS, TextConstants.UNAUTHORIZED_ACESS_MESSAGE);
        }

        ParametersDTO parametersDTO = super.saveOrUpdate(dto);
        ParametersCache.addParameter(parametersDTO);
        return parametersDTO;
    }

    @Override
    public void deleteById(String code) {
        if (!SecurityUtils.hasRole(Role.SYSTEM_ADMINISTRATOR)) {
            throw new UserAuthenticationException(ErrorCode.UNAUTHORIZED_ACESS, TextConstants.UNAUTHORIZED_ACESS_MESSAGE);
        }

        super.deleteById(code);
        ParametersCache.removeParameter(code);
    }
}
