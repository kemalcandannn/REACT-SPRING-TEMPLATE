package com.my_company.base.repository.authentication;

import com.my_company.base.domain.entity.authentication.Role;
import com.my_company.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends BaseRepository<Role, String> {

}
