package com.my_company.repository.authentication;

import com.my_company.domain.entity.authentication.Role;
import com.my_company.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends BaseRepository<Role, String> {
}
