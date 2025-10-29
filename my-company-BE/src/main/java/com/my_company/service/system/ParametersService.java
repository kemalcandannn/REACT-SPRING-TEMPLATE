package com.my_company.service.system;

import com.my_company.cache.ParametersCache;
import com.my_company.constants.TextConstants;
import com.my_company.constants.enums.ErrorCode;
import com.my_company.constants.enums.ParametersCode;
import com.my_company.constants.enums.RoleCode;
import com.my_company.domain.dto.system.ParametersDTO;
import com.my_company.domain.entity.system.Parameters;
import com.my_company.exception.InternalServerException;
import com.my_company.exception.UserAuthenticationException;
import com.my_company.mapper.system.ParametersMapper;
import com.my_company.repository.system.ParametersRepository;
import com.my_company.service.BaseService;
import com.my_company.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
public class ParametersService extends BaseService<Parameters, ParametersDTO, String> {
    public ParametersService(ParametersRepository repository, ParametersMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public ParametersDTO saveOrUpdate(ParametersDTO dto) {
        if (!SecurityUtils.hasRole(RoleCode.SYSTEM_ADMINISTRATOR)) {
            throw new UserAuthenticationException(ErrorCode.DO_NOT_HAVE_PERMISSION, TextConstants.DO_NOT_HAVE_PERMISSION_MESSAGE);
        }

        ParametersDTO parametersDTO = super.saveOrUpdate(dto);
        ParametersCache.addParameter(parametersDTO);
        return parametersDTO;
    }

    @Override
    public void deleteById(String code) {
        if (!SecurityUtils.hasRole(RoleCode.SYSTEM_ADMINISTRATOR)) {
            throw new UserAuthenticationException(ErrorCode.DO_NOT_HAVE_PERMISSION, TextConstants.DO_NOT_HAVE_PERMISSION_MESSAGE);
        }

        if (Arrays
                .stream(ParametersCode.values())
                .anyMatch(parametersCode -> parametersCode.name().equalsIgnoreCase(code))) {

            throw new InternalServerException(ErrorCode.USED_BY_THE_SYSTEM, String.format(TextConstants.USED_BY_THE_SYSTEM_MESSAGE, code));
        }

        super.deleteById(code);
        ParametersCache.removeParameter(code);
    }
}
