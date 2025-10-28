package com.my_company.domain.entity.authentication;

import com.my_company.constants.SchemaConstants;
import com.my_company.constants.TableConstants;
import com.my_company.domain.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = SchemaConstants.AUTHENTICATION, name = TableConstants.MENU)
public class Menu implements BaseEntity<String> {
    @Id
    @Column(name = "CODE", nullable = false)
    private String code;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "PATH", nullable = false)
    private String path;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Override
    public String getId() {
        return this.code;
    }
}
