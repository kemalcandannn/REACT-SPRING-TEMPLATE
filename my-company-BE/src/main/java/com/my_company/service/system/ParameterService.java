package com.my_company.service.system;

import com.my_company.cache.ParameterCache;
import com.my_company.constants.TextConstants;
import com.my_company.constants.enums.ErrorCode;
import com.my_company.constants.enums.ParameterCode;
import com.my_company.constants.enums.RoleCode;
import com.my_company.domain.dto.system.ParameterDTO;
import com.my_company.domain.entity.system.Parameter;
import com.my_company.exception.InternalServerException;
import com.my_company.exception.UserAuthenticationException;
import com.my_company.mapper.system.ParameterMapper;
import com.my_company.repository.system.ParameterRepository;
import com.my_company.service.BaseService;
import com.my_company.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
public class ParameterService extends BaseService<Parameter, ParameterDTO, String> {
    public ParameterService(ParameterRepository repository, ParameterMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public ParameterDTO saveOrUpdate(ParameterDTO dto) {
        if (!SecurityUtils.hasRole(RoleCode.SYSTEM_ADMINISTRATOR)) {
            throw new UserAuthenticationException(ErrorCode.DO_NOT_HAVE_PERMISSION, TextConstants.DO_NOT_HAVE_PERMISSION_MESSAGE);
        }

        ParameterDTO parameterDTO = super.saveOrUpdate(dto);
        ParameterCache.addParameter(parameterDTO);
        return parameterDTO;
    }

    @Override
    public void deleteById(String code) {
        if (!SecurityUtils.hasRole(RoleCode.SYSTEM_ADMINISTRATOR)) {
            throw new UserAuthenticationException(ErrorCode.DO_NOT_HAVE_PERMISSION, TextConstants.DO_NOT_HAVE_PERMISSION_MESSAGE);
        }

        if (Arrays
                .stream(ParameterCode.values())
                .anyMatch(parameterCode -> parameterCode.name().equalsIgnoreCase(code))) {

            throw new InternalServerException(ErrorCode.USED_BY_THE_SYSTEM, String.format(TextConstants.USED_BY_THE_SYSTEM_MESSAGE, code));
        }

        super.deleteById(code);
        ParameterCache.removeParameter(code);
    }
}
