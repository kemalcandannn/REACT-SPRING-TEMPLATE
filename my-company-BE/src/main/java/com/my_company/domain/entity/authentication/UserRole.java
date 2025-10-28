package com.my_company.domain.entity.authentication;

import com.my_company.constants.SchemaConstants;
import com.my_company.constants.TableConstants;
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
public class UserRole {
    @EmbeddedId
    private UserRoleId id;

    @Column(name = "CREATED_AT", updatable = false, insertable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
