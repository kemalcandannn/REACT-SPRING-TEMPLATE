package com.my_company.repository.authentication;

import com.my_company.domain.entity.authentication.RoleMenu;
import com.my_company.domain.entity.authentication.RoleMenuId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMenuRepository extends JpaRepository<RoleMenu, RoleMenuId> {
    List<RoleMenu> findByIdRoleCodeIn(List<String> roleCode);
}
