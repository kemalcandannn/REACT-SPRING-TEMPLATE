package com.my_company.service.authentication;

import com.my_company.domain.dto.authentication.RoleMenuDTO;
import com.my_company.domain.entity.authentication.RoleMenu;
import com.my_company.domain.entity.authentication.RoleMenuId;
import com.my_company.mapper.authentication.RoleMenuMapper;
import com.my_company.repository.authentication.RoleMenuRepository;
import com.my_company.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoleMenuService extends BaseService<RoleMenu, RoleMenuDTO, RoleMenuId> {

    public RoleMenuService(RoleMenuRepository repository, RoleMenuMapper mapper) {
        super(repository, mapper);
    }

}
