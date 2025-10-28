package com.my_company.repository.authentication;

import com.my_company.domain.entity.authentication.Menu;
import com.my_company.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends BaseRepository<Menu, String> {
}
