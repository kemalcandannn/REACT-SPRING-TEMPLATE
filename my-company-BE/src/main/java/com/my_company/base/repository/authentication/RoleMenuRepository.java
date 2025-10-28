package com.my_company.base.repository.authentication;

import com.my_company.base.domain.entity.authentication.RoleMenu;
import com.my_company.base.domain.entity.authentication.RoleMenuId;
import com.my_company.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMenuRepository extends BaseRepository<RoleMenu, RoleMenuId> {

}
