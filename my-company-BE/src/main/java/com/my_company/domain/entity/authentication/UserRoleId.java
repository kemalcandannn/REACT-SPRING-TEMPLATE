package com.my_company.domain.entity.authentication;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserRoleId implements Serializable {

    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Column(name = "ROLE_CODE", nullable = false)
    private String roleCode;

}
