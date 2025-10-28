package com.my_company.service;

import com.my_company.domain.dto.BaseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public interface IBaseService<DTO extends BaseDto<ID>, ID extends Serializable> {
    List<DTO> findAll();

    Page<DTO> findAllWithPageable(Pageable pageable);

    DTO findById(ID id, boolean throwException);

    DTO saveOrUpdate(DTO dto);

    void deleteById(ID id);
}
