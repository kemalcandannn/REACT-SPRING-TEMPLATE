package com.my_company.service;

import com.my_company.constants.ApplicationConstants;
import com.my_company.constants.TextConstants;
import com.my_company.constants.enums.ErrorCode;
import com.my_company.domain.dto.BaseDto;
import com.my_company.domain.entity.BaseEntity;
import com.my_company.exception.BadRequestException;
import com.my_company.exception.ResourceNotFoundException;
import com.my_company.mapper.BaseMapper;
import com.my_company.repository.BaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class BaseService<ENTITY extends BaseEntity<ID>, DTO extends BaseDto<ID>, ID extends Serializable> implements IBaseService<DTO, ID> {
    private final BaseRepository<ENTITY, ID> repository;
    private final BaseMapper<ENTITY, DTO, ID> mapper;

    public List<DTO> findAll() {
        return mapper.entityListToDtoList(repository.findAll());
    }

    public Page<DTO> findAllWithPageable(Pageable pageable) {
        Page<ENTITY> entityPage = repository.findAll(pageable);
        return entityPage.map(mapper::entityToDto);
    }

    public DTO findById(ID id, boolean throwException) {
        if (Objects.isNull(id)) {
            if (throwException) {
                throw new ResourceNotFoundException(ErrorCode.REQUIRED_FIELD, String.format(TextConstants.REQUIRED_FIELD_MESSAGE, ApplicationConstants.ID));
            }

            return null;
        }

        Optional<ENTITY> entityOpt = repository.findById(id);

        if (entityOpt.isPresent()) {
            return mapper.entityToDto(entityOpt.get());
        }

        if (throwException) {
            throw new ResourceNotFoundException(ErrorCode.RECORD_WITH_ID_NOT_FOUND, String.format(TextConstants.RECORD_WITH_ID_NOT_FOUND_MESSAGE, id));
        }

        return null;
    }

    public DTO saveOrUpdate(DTO dto) {
        if (Objects.isNull(dto)) {
            throw new BadRequestException(ErrorCode.REQUIRED_FIELD, String.format(TextConstants.REQUIRED_FIELD_MESSAGE, ApplicationConstants.REQUEST_BODY));
        }

        if (Objects.nonNull(dto.getId())) {
            findById(dto.getId(), true);
        }

        ENTITY entity = mapper.dtoToEntity(dto);
        repository.save(entity);
        return mapper.entityToDto(entity);
    }

    public void deleteById(ID id) {
        DTO dto = findById(id, true);
        repository.delete(mapper.dtoToEntity(dto));
    }
}
