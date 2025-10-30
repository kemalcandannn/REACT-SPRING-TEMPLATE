package com.my_company.service.authentication;

import com.my_company.domain.dto.authentication.UserTokenDTO;
import com.my_company.domain.entity.authentication.UserToken;
import com.my_company.mapper.authentication.UserTokenMapper;
import com.my_company.repository.authentication.UserTokenRepository;
import com.my_company.service.BaseService;
import com.my_company.utils.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class UserTokenService extends BaseService<UserToken, UserTokenDTO, Long> {
    private final UserTokenRepository repository;
    private final UserTokenMapper mapper;

    public UserTokenService(UserTokenRepository repository, UserTokenMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    public UserTokenDTO findByToken(String token) {
        List<UserToken> userTokenList = repository.findByToken(token);
        return CollectionUtils.isNotEmpty(userTokenList) ? mapper.entityToDto(userTokenList.get(0)) : null;
    }

    public String getRandomToken() {
        String token = UUID.randomUUID().toString();
        while (Objects.nonNull(findByToken(token))) {
            token = UUID.randomUUID().toString();
        }

        return token;
    }
}
