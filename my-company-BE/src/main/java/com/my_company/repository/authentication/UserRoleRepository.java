package com.my_company.repository.authentication;

import com.my_company.domain.entity.authentication.UserRole;
import com.my_company.domain.entity.authentication.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
    List<UserRole> findByIdUsername(String username);
}
