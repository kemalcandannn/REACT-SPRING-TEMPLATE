package com.my_company.base.service.authentication;

import com.my_company.base.domain.dto.authentication.UserRoleDTO;
import com.my_company.base.domain.entity.authentication.UserRole;
import com.my_company.base.domain.entity.authentication.UserRoleId;
import com.my_company.base.mapper.authentication.UserRoleMapper;
import com.my_company.base.repository.authentication.UserRoleRepository;
import com.my_company.base.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserRoleService extends BaseService<UserRole, UserRoleDTO, UserRoleId> {

    public UserRoleService(UserRoleRepository repository, UserRoleMapper mapper) {
        super(repository, mapper);
    }

}
