package com.my_company.service.authentication;

import com.my_company.constants.ApplicationConstants;
import com.my_company.constants.TextConstants;
import com.my_company.constants.enums.ErrorCode;
import com.my_company.domain.dto.authentication.UserRoleDTO;
import com.my_company.domain.entity.authentication.UserRole;
import com.my_company.domain.entity.authentication.UserRoleId;
import com.my_company.exception.BadRequestException;
import com.my_company.exception.ResourceNotFoundException;
import com.my_company.mapper.authentication.UserRoleMapper;
import com.my_company.repository.authentication.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserRoleService {
    private final UserRoleRepository repository;
    private final UserRoleMapper mapper;

    public List<UserRoleDTO> findAll() {
        return mapper.entityListToDtoList(repository.findAll());
    }

    public Page<UserRoleDTO> findAllWithPageable(Pageable pageable) {
        Page<UserRole> userRolePage = repository.findAll(pageable);
        return userRolePage.map(mapper::entityToDto);
    }

    public UserRoleDTO findByUsernameAndRoleCode(String username, String roleCode, boolean throwException) {
        if (Objects.isNull(username) || Objects.isNull(roleCode)) {
            if (throwException) {
                throw new ResourceNotFoundException(ErrorCode.REQUIRED_FIELD,
                        String.format(TextConstants.REQUIRED_FIELD_MESSAGE,
                                String.join("-", List.of(ApplicationConstants.USERNAME, ApplicationConstants.ROLE_CODE))));
            }

            return null;
        }

        Optional<UserRole> userRoleOpt = repository.findById(
                UserRoleId
                        .builder()
                        .username(username)
                        .roleCode(roleCode)
                        .build()
        );

        if (userRoleOpt.isPresent()) {
            return mapper.entityToDto(userRoleOpt.get());
        }

        if (throwException) {
            throw new ResourceNotFoundException(ErrorCode.RECORD_WITH_ID_NOT_FOUND,
                    String.format(TextConstants.RECORD_WITH_ID_NOT_FOUND_MESSAGE,
                            String.join("-", List.of(username, roleCode))));
        }

        return null;
    }

    public UserRoleDTO saveOrUpdate(UserRoleDTO userRoleDTO) {
        if (Objects.isNull(userRoleDTO)) {
            throw new BadRequestException(ErrorCode.REQUIRED_FIELD,
                    String.format(TextConstants.REQUIRED_FIELD_MESSAGE,
                            ApplicationConstants.REQUEST_BODY));
        }

        if (Objects.nonNull(userRoleDTO.getUsername()) && Objects.nonNull(userRoleDTO.getRoleCode())) {
            findByUsernameAndRoleCode(userRoleDTO.getUsername(), userRoleDTO.getRoleCode(), true);
        }

        UserRole userRole = mapper.dtoToEntity(userRoleDTO);
        repository.save(userRole);
        return mapper.entityToDto(userRole);
    }

    public void deleteByUsernameAndRoleCode(String username, String roleCode) {
        UserRoleDTO userRoleDTO = findByUsernameAndRoleCode(username, roleCode, true);
        repository.delete(mapper.dtoToEntity(userRoleDTO));
    }
}
