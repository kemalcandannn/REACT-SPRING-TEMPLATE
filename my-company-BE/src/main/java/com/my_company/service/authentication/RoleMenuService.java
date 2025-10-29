package com.my_company.service.authentication;

import com.my_company.constants.ApplicationConstants;
import com.my_company.constants.TextConstants;
import com.my_company.constants.enums.ErrorCode;
import com.my_company.domain.dto.authentication.RoleMenuDTO;
import com.my_company.domain.entity.authentication.RoleMenu;
import com.my_company.domain.entity.authentication.RoleMenuId;
import com.my_company.exception.BadRequestException;
import com.my_company.exception.ResourceNotFoundException;
import com.my_company.mapper.authentication.RoleMenuMapper;
import com.my_company.repository.authentication.RoleMenuRepository;
import com.my_company.utils.StringUtils;
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
public class RoleMenuService {
    private final RoleMenuRepository repository;
    private final RoleMenuMapper mapper;

    public List<RoleMenuDTO> findAll() {
        return mapper.entityListToDtoList(repository.findAll());
    }

    public Page<RoleMenuDTO> findAllWithPageable(Pageable pageable) {
        Page<RoleMenu> roleMenuPage = repository.findAll(pageable);
        return roleMenuPage.map(mapper::entityToDto);
    }

    public RoleMenuDTO findByRoleCodeAndMenuCode(String roleCode, String menuCode, boolean throwException) {
        if (StringUtils.isNullOrBlank(roleCode) || StringUtils.isNullOrBlank(menuCode)) {
            if (throwException) {
                throw new ResourceNotFoundException(ErrorCode.REQUIRED_FIELD,
                        String.format(TextConstants.REQUIRED_FIELD_MESSAGE,
                                String.join("-", List.of(ApplicationConstants.ROLE_CODE, ApplicationConstants.MENU_CODE))));
            }

            return null;
        }

        Optional<RoleMenu> roleMenuOpt = repository.findById(
                RoleMenuId
                        .builder()
                        .roleCode(roleCode)
                        .menuCode(menuCode)
                        .build()
        );

        if (roleMenuOpt.isPresent()) {
            return mapper.entityToDto(roleMenuOpt.get());
        }

        if (throwException) {
            throw new ResourceNotFoundException(ErrorCode.RECORD_WITH_ID_NOT_FOUND,
                    String.format(TextConstants.RECORD_WITH_ID_NOT_FOUND_MESSAGE,
                            String.join("-", List.of(roleCode, menuCode))));
        }

        return null;
    }

    public RoleMenuDTO saveOrUpdate(RoleMenuDTO roleMenuDTO) {
        if (Objects.isNull(roleMenuDTO)) {
            throw new BadRequestException(ErrorCode.REQUIRED_FIELD,
                    String.format(TextConstants.REQUIRED_FIELD_MESSAGE,
                            ApplicationConstants.REQUEST_BODY));
        }

        if (Objects.nonNull(roleMenuDTO.getRoleCode()) && Objects.nonNull(roleMenuDTO.getMenuCode())) {
            findByRoleCodeAndMenuCode(roleMenuDTO.getRoleCode(), roleMenuDTO.getMenuCode(), true);
        }

        RoleMenu roleMenu = mapper.dtoToEntity(roleMenuDTO);
        repository.save(roleMenu);
        return mapper.entityToDto(roleMenu);
    }

    public void deleteByRoleCodeAndMenuCode(String roleCode, String menuCode) {
        RoleMenuDTO roleMenuDTO = findByRoleCodeAndMenuCode(roleCode, menuCode, true);
        repository.delete(mapper.dtoToEntity(roleMenuDTO));
    }
}
