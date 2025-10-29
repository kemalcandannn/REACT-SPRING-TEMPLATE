package com.my_company.domain.entity.system;

import com.my_company.constants.SchemaConstants;
import com.my_company.constants.TableConstants;
import com.my_company.constants.enums.ParametersType;
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
@Table(schema = SchemaConstants.SYSTEM, name = TableConstants.PARAMETERS)
public class Parameters implements BaseEntity<String> {
    @Id
    @Column(name = "CODE", nullable = false)
    private String code;

    @Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private ParametersType type;

    @Column(name = "VALUE", nullable = false)
    private String value;

    @Column(name = "CREATED_AT", updatable = false, insertable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Override
    public String getId() {
        return this.code;
    }
}
