package com.my_company.base.service.authentication;

import com.my_company.base.domain.dto.authentication.MenuDTO;
import com.my_company.base.domain.entity.authentication.Menu;
import com.my_company.base.mapper.authentication.MenuMapper;
import com.my_company.base.repository.authentication.MenuRepository;
import com.my_company.base.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MenuService extends BaseService<Menu, MenuDTO, String> {

    public MenuService(MenuRepository repository, MenuMapper mapper) {
        super(repository, mapper);
    }

}
