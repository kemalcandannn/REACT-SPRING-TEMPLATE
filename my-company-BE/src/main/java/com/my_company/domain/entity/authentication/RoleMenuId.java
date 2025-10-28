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
public class RoleMenuId implements Serializable {

    @Column(name = "ROLE_CODE", nullable = false)
    private String roleCode;

    @Column(name = "MENU_CODE", nullable = false)
    private String menuCode;

}
