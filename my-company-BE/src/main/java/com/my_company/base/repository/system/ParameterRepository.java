package com.my_company.base.repository.system;

import com.my_company.base.domain.entity.system.Parameter;
import com.my_company.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParameterRepository extends BaseRepository<Parameter, String> {

}
