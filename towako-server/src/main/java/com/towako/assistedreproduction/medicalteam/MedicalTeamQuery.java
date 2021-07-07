package com.towako.assistedreproduction.medicalteam;

import com.cartisan.repositories.Condition;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MedicalTeamQuery {
    @ApiModelProperty(value = "病历Id")
    @NotNull(message = "病历Id不能为空")
    @Condition(propName = "medicalRecordId", type = Condition.Type.EQUAL)
    private Long medicalRecordId;
}
