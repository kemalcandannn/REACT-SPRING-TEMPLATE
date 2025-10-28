package com.my_company.base.domain.entity.authentication;

import com.my_company.base.constants.SchemaConstants;
import com.my_company.base.constants.TableConstants;
import com.my_company.base.constants.enums.AuthProvider;
import com.my_company.base.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = SchemaConstants.AUTHENTICATION, name = TableConstants.USER)
public class User implements BaseEntity<String> {

    @Id
    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "PASSWORD2")
    private String password2;

    @Column(name = "PASSWORD3")
    private String password3;

    @Column(name = "PROVIDER", nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @Column(name = "PROVIDER_ID")
    private String providerId;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Override
    public String getId() {
        return this.username;
    }

}
