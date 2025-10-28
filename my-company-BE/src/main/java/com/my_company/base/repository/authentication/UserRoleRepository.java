package com.my_company.base.repository.authentication;

import com.my_company.base.domain.entity.authentication.UserRole;
import com.my_company.base.domain.entity.authentication.UserRoleId;
import com.my_company.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends BaseRepository<UserRole, UserRoleId> {

}
