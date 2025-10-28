package com.my_company.service.authentication;

import com.my_company.domain.dto.authentication.MenuDTO;
import com.my_company.domain.entity.authentication.Menu;
import com.my_company.mapper.authentication.MenuMapper;
import com.my_company.repository.authentication.MenuRepository;
import com.my_company.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MenuService extends BaseService<Menu, MenuDTO, String> {
    public MenuService(MenuRepository repository, MenuMapper mapper) {
        super(repository, mapper);
    }
}
