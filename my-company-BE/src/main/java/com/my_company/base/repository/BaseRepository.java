package com.my_company.base.repository;

import com.my_company.base.domain.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<ENTITY extends BaseEntity<ID>, ID extends Serializable> extends JpaRepository<ENTITY, ID> {

}
