package com.my_company.service.authentication;

import com.my_company.domain.dto.authentication.UserRoleDTO;
import com.my_company.domain.entity.authentication.UserRole;
import com.my_company.domain.entity.authentication.UserRoleId;
import com.my_company.mapper.authentication.UserRoleMapper;
import com.my_company.repository.authentication.UserRoleRepository;
import com.my_company.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserRoleService extends BaseService<UserRole, UserRoleDTO, UserRoleId> {

    public UserRoleService(UserRoleRepository repository, UserRoleMapper mapper) {
        super(repository, mapper);
    }

}
