package com.my_company.repository.authentication;

import com.my_company.constants.enums.TokenStatus;
import com.my_company.domain.entity.authentication.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
    List<UserToken> findByToken(String token);

    List<UserToken> findByTokenAndStatus(String token, TokenStatus status);
}
