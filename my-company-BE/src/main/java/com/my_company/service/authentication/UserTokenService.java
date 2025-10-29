package com.my_company.service.authentication;

import com.my_company.constants.ApplicationConstants;
import com.my_company.constants.TextConstants;
import com.my_company.constants.enums.ErrorCode;
import com.my_company.constants.enums.TokenStatus;
import com.my_company.domain.dto.authentication.UserTokenDTO;
import com.my_company.domain.entity.authentication.UserToken;
import com.my_company.exception.BadRequestException;
import com.my_company.exception.ResourceNotFoundException;
import com.my_company.mapper.authentication.UserTokenMapper;
import com.my_company.repository.authentication.UserTokenRepository;
import com.my_company.utils.CollectionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserTokenService {
    private final UserTokenRepository repository;
    private final UserTokenMapper mapper;

    public UserTokenDTO findById(Long id, boolean throwException) {
        if (Objects.isNull(id)) {
            if (throwException) {
                throw new ResourceNotFoundException(ErrorCode.REQUIRED_FIELD, String.format(TextConstants.REQUIRED_FIELD_MESSAGE, ApplicationConstants.ID));
            }

            return null;
        }

        Optional<UserToken> entityOpt = repository.findById(id);

        if (entityOpt.isPresent()) {
            return mapper.entityToDto(entityOpt.get());
        }

        if (throwException) {
            throw new ResourceNotFoundException(ErrorCode.RECORD_WITH_ID_NOT_FOUND, String.format(TextConstants.RECORD_WITH_ID_NOT_FOUND_MESSAGE, id));
        }

        return null;
    }

    public UserTokenDTO saveOrUpdate(UserTokenDTO userTokenDTO) {
        if (Objects.isNull(userTokenDTO)) {
            throw new BadRequestException(ErrorCode.REQUIRED_FIELD, String.format(TextConstants.REQUIRED_FIELD_MESSAGE, ApplicationConstants.REQUEST_BODY));
        }

        if (Objects.nonNull(userTokenDTO.getId())) {
            findById(userTokenDTO.getId(), true);
        }

        UserToken userToken = mapper.dtoToEntity(userTokenDTO);
        repository.save(userToken);
        return mapper.entityToDto(userToken);
    }

    public UserTokenDTO findByToken(String token) {
        List<UserToken> userTokenList = repository.findByToken(token);
        return CollectionUtils.isNotEmpty(userTokenList) ? mapper.entityToDto(userTokenList.get(0)) : null;
    }

    public UserTokenDTO findByTokenAndStatus(String token, TokenStatus status) {
        List<UserToken> userTokenList = repository.findByTokenAndStatus(token, status);
        return CollectionUtils.isNotEmpty(userTokenList) ? mapper.entityToDto(userTokenList.get(0)) : null;
    }

}
