package com.my_company.base.domain.entity.authentication;

import com.my_company.base.constants.SchemaConstants;
import com.my_company.base.constants.TableConstants;
import com.my_company.base.domain.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = SchemaConstants.AUTHENTICATION, name = TableConstants.USER_ROLE)
public class UserRole implements BaseEntity<UserRoleId> {

    @EmbeddedId
    private UserRoleId id;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt = LocalDateTime.now();

}
