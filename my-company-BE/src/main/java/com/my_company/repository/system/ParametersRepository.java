package com.my_company.repository.system;

import com.my_company.domain.entity.system.Parameters;
import com.my_company.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParametersRepository extends BaseRepository<Parameters, String> {
}
