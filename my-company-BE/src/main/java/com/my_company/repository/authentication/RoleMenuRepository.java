package com.my_company.repository.authentication;

import com.my_company.domain.entity.authentication.RoleMenu;
import com.my_company.domain.entity.authentication.RoleMenuId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMenuRepository extends JpaRepository<RoleMenu, RoleMenuId> {
}
