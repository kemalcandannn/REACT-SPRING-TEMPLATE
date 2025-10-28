package com.my_company.service.authentication;

import com.my_company.domain.dto.authentication.RoleDTO;
import com.my_company.domain.entity.authentication.Role;
import com.my_company.mapper.authentication.RoleMapper;
import com.my_company.repository.authentication.RoleRepository;
import com.my_company.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoleService extends BaseService<Role, RoleDTO, String> {

    public RoleService(RoleRepository repository, RoleMapper mapper) {
        super(repository, mapper);
    }

}
