package com.my_company.repository.system;

import com.my_company.domain.entity.system.Parameter;
import com.my_company.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParameterRepository extends BaseRepository<Parameter, String> {
}
