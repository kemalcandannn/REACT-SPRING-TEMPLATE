package com.my_company.base.service.authentication;

import com.my_company.base.domain.dto.authentication.RoleMenuDTO;
import com.my_company.base.domain.entity.authentication.RoleMenu;
import com.my_company.base.domain.entity.authentication.RoleMenuId;
import com.my_company.base.mapper.authentication.RoleMenuMapper;
import com.my_company.base.repository.authentication.RoleMenuRepository;
import com.my_company.base.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoleMenuService extends BaseService<RoleMenu, RoleMenuDTO, RoleMenuId> {

    public RoleMenuService(RoleMenuRepository repository, RoleMenuMapper mapper) {
        super(repository, mapper);
    }

}
