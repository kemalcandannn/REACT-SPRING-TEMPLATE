package com.my_company.domain.request.system;

import com.my_company.domain.dto.system.ParameterDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAllParameterRequest {
    private List<ParameterDTO> parameterList;
}
