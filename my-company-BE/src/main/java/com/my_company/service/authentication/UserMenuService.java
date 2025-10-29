package com.my_company.service.authentication;

import com.my_company.constants.ApplicationConstants;
import com.my_company.constants.TextConstants;
import com.my_company.constants.enums.ErrorCode;
import com.my_company.domain.dto.authentication.UserMenuDTO;
import com.my_company.domain.entity.authentication.UserMenu;
import com.my_company.domain.entity.authentication.UserMenuId;
import com.my_company.exception.BadRequestException;
import com.my_company.exception.ResourceNotFoundException;
import com.my_company.mapper.authentication.UserMenuMapper;
import com.my_company.repository.authentication.UserMenuRepository;
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
public class UserMenuService {
    private final UserMenuRepository repository;
    private final UserMenuMapper mapper;

    public List<UserMenuDTO> findAll() {
        return mapper.entityListToDtoList(repository.findAll());
    }

    public Page<UserMenuDTO> findAllWithPageable(Pageable pageable) {
        Page<UserMenu> userMenuPage = repository.findAll(pageable);
        return userMenuPage.map(mapper::entityToDto);
    }

    public UserMenuDTO findByUsernameAndMenuCode(String username, String menuCode, boolean throwException) {
        if (StringUtils.isNullOrBlank(username) || StringUtils.isNullOrBlank(menuCode)) {
            if (throwException) {
                throw new ResourceNotFoundException(ErrorCode.REQUIRED_FIELD,
                        String.format(TextConstants.REQUIRED_FIELD_MESSAGE,
                                String.join("-", List.of(ApplicationConstants.USERNAME, ApplicationConstants.MENU_CODE))));
            }

            return null;
        }

        Optional<UserMenu> userMenuOpt = repository.findById(
                UserMenuId
                        .builder()
                        .username(username)
                        .menuCode(menuCode)
                        .build()
        );

        if (userMenuOpt.isPresent()) {
            return mapper.entityToDto(userMenuOpt.get());
        }

        if (throwException) {
            throw new ResourceNotFoundException(ErrorCode.RECORD_WITH_ID_NOT_FOUND,
                    String.format(TextConstants.RECORD_WITH_ID_NOT_FOUND_MESSAGE,
                            String.join("-", List.of(username, menuCode))));
        }

        return null;
    }

    public UserMenuDTO saveOrUpdate(UserMenuDTO userMenuDTO) {
        if (Objects.isNull(userMenuDTO)) {
            throw new BadRequestException(ErrorCode.REQUIRED_FIELD,
                    String.format(TextConstants.REQUIRED_FIELD_MESSAGE,
                            ApplicationConstants.REQUEST_BODY));
        }

        if (Objects.nonNull(userMenuDTO.getUsername()) && Objects.nonNull(userMenuDTO.getMenuCode())) {
            findByUsernameAndMenuCode(userMenuDTO.getUsername(), userMenuDTO.getMenuCode(), true);
        }

        UserMenu userMenu = mapper.dtoToEntity(userMenuDTO);
        repository.save(userMenu);
        return mapper.entityToDto(userMenu);
    }

    public void deleteByUsernameAndMenuCode(String username, String menuCode) {
        UserMenuDTO userMenuDTO = findByUsernameAndMenuCode(username, menuCode, true);
        repository.delete(mapper.dtoToEntity(userMenuDTO));
    }

    public List<UserMenuDTO> findByUsername(String username) {
        if (StringUtils.isNullOrBlank(username)) {
            throw new BadRequestException(ErrorCode.REQUIRED_FIELD,
                    String.format(TextConstants.REQUIRED_FIELD_MESSAGE,
                            ApplicationConstants.USERNAME));
        }

        List<UserMenu> userMenuList = repository.findByIdUsername(username);
        return mapper.entityListToDtoList(userMenuList);
    }
}
