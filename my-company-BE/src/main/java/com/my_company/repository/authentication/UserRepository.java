package com.my_company.repository.authentication;

import com.my_company.domain.entity.authentication.User;
import com.my_company.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User, String> {
}
