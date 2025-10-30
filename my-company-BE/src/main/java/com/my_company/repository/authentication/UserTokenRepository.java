package com.my_company.repository.authentication;

import com.my_company.domain.entity.authentication.UserToken;
import com.my_company.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTokenRepository extends BaseRepository<UserToken, Long> {
    List<UserToken> findByToken(String token);
}
