package com.my_company.base.domain.entity.system;

import com.my_company.base.constants.SchemaConstants;
import com.my_company.base.constants.TableConstants;
import com.my_company.base.constants.enums.ParameterType;
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
@Table(schema = SchemaConstants.SYSTEM, name = TableConstants.PARAMETER)
public class Parameter implements BaseEntity<String> {

    @Id
    @Column(name = "CODE", nullable = false)
    private String code;

    @Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private ParameterType type;

    @Column(name = "VALUE", nullable = false)
    private String value;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Override
    public String getId() {
        return this.code;
    }
}
