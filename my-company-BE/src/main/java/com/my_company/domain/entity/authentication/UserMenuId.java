package com.my_company.domain.entity.authentication;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Builder
public class UserMenuId implements Serializable {
    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Column(name = "MENU_CODE", nullable = false)
    private String menuCode;
}
