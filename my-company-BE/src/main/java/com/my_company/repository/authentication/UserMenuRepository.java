package com.my_company.repository.authentication;

import com.my_company.domain.entity.authentication.UserMenu;
import com.my_company.domain.entity.authentication.UserMenuId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMenuRepository extends JpaRepository<UserMenu, UserMenuId> {
    List<UserMenu> findByIdUsername(String username);
}
