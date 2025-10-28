package com.my_company.repository.authentication;

import com.my_company.domain.entity.authentication.RoleMenu;
import com.my_company.domain.entity.authentication.RoleMenuId;
import com.my_company.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMenuRepository extends BaseRepository<RoleMenu, RoleMenuId> {

}
