package com.my_company.domain.entity.authentication;

import com.my_company.constants.SchemaConstants;
import com.my_company.constants.SequenceConstants;
import com.my_company.constants.TableConstants;
import com.my_company.constants.enums.TokenStatus;
import com.my_company.constants.enums.TokenType;
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
@Table(schema = SchemaConstants.AUTHENTICATION, name = TableConstants.USER_TOKEN)
public class UserToken implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TableConstants.USER_TOKEN)
    @SequenceGenerator(sequenceName = SequenceConstants.USER_TOKEN_SEQ, name = TableConstants.USER_TOKEN, allocationSize = 1)
    private Long id;

    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false)
    private TokenType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private TokenStatus status;

    @Column(name = "TOKEN", unique = true, nullable = false)
    private String token;

    @Column(name = "EXPIRES_AT")
    private LocalDateTime expiresAt;

    @Column(name = "USED_AT")
    private LocalDateTime usedAt;

    @Column(name = "IP_ADDRESS")
    private String ipAddress;

    @Column(name = "CREATED_AT", updatable = false, insertable = false)
    private LocalDateTime createdAt;
}
