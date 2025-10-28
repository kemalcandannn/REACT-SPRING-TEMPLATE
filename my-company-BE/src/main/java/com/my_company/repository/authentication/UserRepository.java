package com.my_company.repository.authentication;

import com.my_company.domain.entity.authentication.User;
import com.my_company.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends BaseRepository<User, String> {

    List<User> findByUsername(String username);

}
