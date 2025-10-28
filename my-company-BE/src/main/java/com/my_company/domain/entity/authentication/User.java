package com.my_company.domain.entity.authentication;

import com.my_company.constants.SchemaConstants;
import com.my_company.constants.TableConstants;
import com.my_company.constants.enums.AuthProvider;
import com.my_company.constants.enums.Status;
import com.my_company.domain.entity.BaseEntity;
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

    @Column(name = "PASSWORD_VALID_UNTIL")
    private LocalDateTime passwordValidUntil;

    @Column(name = "PROVIDER", nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @Column(name = "PROVIDER_ID")
    private String providerId;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Override
    public String getId() {
        return this.username;
    }

}
