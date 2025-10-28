package com.my_company.base.service.authentication;

import com.my_company.base.domain.dto.authentication.RoleDTO;
import com.my_company.base.domain.entity.authentication.Role;
import com.my_company.base.mapper.authentication.RoleMapper;
import com.my_company.base.repository.authentication.RoleRepository;
import com.my_company.base.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoleService extends BaseService<Role, RoleDTO, String> {

    public RoleService(RoleRepository repository, RoleMapper mapper) {
        super(repository, mapper);
    }

}
