package com.my_company.service.system;

import com.my_company.cache.ParameterCache;
import com.my_company.constants.ApplicationConstants;
import com.my_company.constants.TextConstants;
import com.my_company.constants.enums.ErrorCode;
import com.my_company.constants.enums.ParameterCode;
import com.my_company.constants.enums.RoleCode;
import com.my_company.domain.dto.system.ParameterDTO;
import com.my_company.domain.entity.system.Parameter;
import com.my_company.domain.request.system.UpdateAllParameterRequest;
import com.my_company.exception.BadRequestException;
import com.my_company.exception.InternalServerException;
import com.my_company.exception.UserAuthenticationException;
import com.my_company.mapper.system.ParameterMapper;
import com.my_company.repository.system.ParameterRepository;
import com.my_company.service.BaseService;
import com.my_company.utils.CollectionUtils;
import com.my_company.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class ParameterService extends BaseService<Parameter, ParameterDTO, String> {
    private final ParameterRepository repository;
    private final ParameterMapper mapper;

    public ParameterService(ParameterRepository repository, ParameterMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
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

    public void updateAllParameters(UpdateAllParameterRequest request) {
        if (Objects.isNull(request)) {
            throw new BadRequestException(ErrorCode.REQUIRED_FIELD, String.format(TextConstants.REQUIRED_FIELD_MESSAGE, ApplicationConstants.REQUEST_BODY));
        }

        if(CollectionUtils.isNotEmpty(request.getParameterList())){
            Map<String, ParameterDTO> existParameterMap = new HashMap<>();
            request.getParameterList().forEach(parameterDTO -> {
                ParameterDTO existParameter = findById(parameterDTO.getId(), false);
                if(Objects.nonNull(existParameter)){
                    existParameterMap.put(parameterDTO.getId(), parameterDTO);
                }
            });

            if(!existParameterMap.isEmpty()){
                repository.saveAll(mapper.dtoListToEntityList(new ArrayList<>(existParameterMap.values())));
            }
        }
    }
}
