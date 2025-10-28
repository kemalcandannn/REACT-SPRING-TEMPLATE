package com.my_company.service.authentication;

import com.my_company.constants.TextConstants;
import com.my_company.constants.enums.ErrorCode;
import com.my_company.domain.dto.authentication.RoleDTO;
import com.my_company.domain.entity.authentication.Role;
import com.my_company.exception.BadRequestException;
import com.my_company.mapper.authentication.RoleMapper;
import com.my_company.repository.authentication.RoleRepository;
import com.my_company.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
public class RoleService extends BaseService<Role, RoleDTO, String> {
    public RoleService(RoleRepository repository, RoleMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public void deleteById(String code) {
        if (Arrays
                .stream(com.my_company.constants.enums.Role.values())
                .anyMatch(role -> role.name().equalsIgnoreCase(code))) {

            throw new BadRequestException(ErrorCode.USED_BY_THE_SYSTEM, String.format(TextConstants.USED_BY_THE_SYSTEM_MESSAGE, code));
        }

        super.deleteById(code);
    }
}
